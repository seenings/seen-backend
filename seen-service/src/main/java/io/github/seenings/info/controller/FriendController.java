package io.github.seenings.info.controller;

import static com.google.common.collect.Lists.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import io.github.seenings.busi.controller.BusiController;
import io.github.seenings.busi.model.Busi;
import io.github.seenings.coin.constant.CoinConstant;
import io.github.seenings.task.api.DoTaskApi;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.github.seenings.apply.http.HttpUserApplyAgreeService;
import io.github.seenings.apply.http.HttpUserApplyLookService;
import io.github.seenings.apply.http.HttpUserApplyRefuseService;
import io.github.seenings.apply.http.HttpUserApplyService;
import io.github.seenings.apply.http.HttpUserApplyTradeService;
import io.github.seenings.article.enumeration.ContentType;
import io.github.seenings.chat.enumeration.ApplyStatus;
import io.github.seenings.chat.http.HttpChatHistoryService;
import io.github.seenings.chat.http.HttpChatUserService;
import io.github.seenings.chat.utils.ChatApplyUtils;
import io.github.seenings.coin.enumeration.BusiType;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.info.model.RecApplyContent;
import io.github.seenings.info.model.SendApplyContent;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.text.http.HttpTextService;
import io.github.seenings.trade.http.HttpCoinTradeService;

import cn.hutool.core.lang.Pair;
import lombok.extern.slf4j.Slf4j;

/**
 * FriendController
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.REST + "user/friend")
public class FriendController {

    private HttpChatUserService httpChatUserService;

    private HttpCoinTradeService httpCoinTradeService;

    private HttpUserApplyService httpUserApplyService;

    private HttpUserApplyTradeService httpUserApplyTradeService;

    private HttpTextService httpTextService;
    /**
     * 业务
     */
    private BusiController busiController;
    /**
     * 做任务
     */
    private DoTaskApi doTaskApi;

    @PostMapping("apply-friend")
    public R<Boolean> applyFriend(
            @SessionAttribute Long userId, @RequestParam("appliedUserId") Long appliedUserId) {
        if (Objects.equals(userId, appliedUserId)) {
            return ResUtils.ok(false, "当前为自己无需添加好友");
        }
        // 1.检查当前是否好友
        Boolean isFriend = httpChatUserService
                .friendUserIdIsFriend(Collections.singleton(appliedUserId), userId)
                .get(appliedUserId);
        if (isFriend) {
            String msg = "已经是好朋友，无需重复申请。";
            log.warn(msg);
            return ResUtils.ok(false, msg);
        }
        // 2.检查虚拟币是否足够（100），并扣除虚拟币（冻结）

        Boolean enough = httpCoinTradeService.checkEnough(userId, (long) CoinConstant.APPLY_SPEND_COIN_AMOUNT);
        if (!enough) {
            // 没有足够的虚拟币
            String msg = String.format("没有足够的虚拟币，用户ID是：%s，朋友ID是：%s。", userId, appliedUserId);
            log.warn(msg);
            return ResUtils.ok(false, msg);
        }
        //足够，则直接扣100
        LocalDateTime now = LocalDateTime.now();
        long busiId = busiController.insert(new Busi().setBusiTime(now).setBusiTypeId(BusiType.APPLY_DO_FRIEND.getIndex()));
        // 3.添加一条好友申请记录
        // 3.1 添加好友申请文本
        String text = "很高兴认识你";
        Integer textId = httpTextService.saveAndReturnId(text);
        // 3.2 添加好友申请记录
        Integer applyId = httpUserApplyService.set(userId, textId, appliedUserId);
        httpUserApplyTradeService.set(applyId, List.of(busiId));

        doTaskApi.doTaskGetCoin(userId, busiId, (long) CoinConstant.APPLY_SPEND_COIN_AMOUNT);

        // TODO，生成一条通知消息
        return ResUtils.ok(true);
    }

    @PostMapping("re-apply")
    public R<Boolean> reApply(@SessionAttribute Long userId, @RequestParam("pageUserId") Long pageUserId) {

        // 1.检查当前是否好友
        Boolean isFriend = httpChatUserService
                .friendUserIdIsFriend(Collections.singleton(pageUserId), userId)
                .get(pageUserId);
        if (isFriend) {
            String msg = String.format("已经是好朋友，用户ID是：%s，朋友ID是：%s。", userId, pageUserId);
            log.warn(msg);
            return ResUtils.ok(false, msg);
        }
        // 2.检查虚拟币是否足够（100，再次申请2倍），并扣除虚拟币（冻结）
        LocalDateTime now = LocalDateTime.now();
        long busiId = busiController.insert(new Busi().setBusiTime(now).setBusiTypeId(BusiType.APPLY_DO_FRIEND.getIndex()));

        long coinMount = (long) CoinConstant.APPLY_SPEND_COIN_AMOUNT * 2;
        Boolean enough = httpCoinTradeService.checkEnough(userId, coinMount);
        if (!enough) {
            // 没有足够的虚拟币
            String msg = String.format("没有足够的虚拟币，用户ID是：%s，朋友ID是：%s。", userId, pageUserId);
            log.warn(msg);
            return ResUtils.ok(false, msg);
        }
        // 3.添加一条好友申请记录
        // 3.1 添加好友申请文本
        String text = "很高兴认识你";
        Integer textId = httpTextService.saveAndReturnId(text);
        // 3.2 添加好友申请记录
        httpUserApplyService.set(userId, textId, pageUserId);

        doTaskApi.doTaskGetCoin(userId, busiId, coinMount);

        // TODO，生成一条通知消息
        return ResUtils.ok(true);
    }

    private HttpUserApplyAgreeService httpUserApplyAgreeService;

    private HttpUserApplyLookService httpUserApplyLookService;
    private HttpUserApplyRefuseService httpUserApplyRefuseService;

    @PostMapping("look")
    public R<Integer> look(
            @RequestParam("applyId") Integer applyId) {
        //1.收集用户查看记录
        Integer lookId = httpUserApplyLookService.set(applyId, LocalDateTime.now());
        return ResUtils.ok(lookId);
    }

    @PostMapping("agree")
    public R<Long> agree(@SessionAttribute Long userId,
                         @RequestParam("applyId") Integer applyId, @RequestParam("pageUserId") Long pageUserId) {

        //TODO 同意是否需要收玫瑰币
        LocalDateTime now = LocalDateTime.now();
        long busiId = busiController.insert(new Busi().setBusiTime(now).setBusiTypeId(BusiType.AGREE_DO_FRIEND.getIndex()));

        httpUserApplyTradeService.set(applyId, Collections.singletonList(busiId));
        //2.写同意记录到表中
        httpUserApplyAgreeService.set(applyId, LocalDateTime.now());
        //3.添加用户到好友列表
        httpChatUserService.set(userId, pageUserId);
        httpChatUserService.set(pageUserId, userId);
        //4.添加聊天消息
        LocalDateTime applyTime = httpUserApplyService.applyIdToApplyTime(Collections.singleton(applyId)).get(applyId);
        Integer applyTextId = httpUserApplyService.applyIdToTextId(Collections.singleton(applyId)).get(applyId);
        httpChatHistoryService.add(ContentType.TEXT, applyTextId,
                userId, pageUserId, false, applyTime);
//同意是否需要收玫瑰币
//        doTaskApi.doTaskGetCoin(userId, busiId, (long) CoinConstant.APPLY_SPEND_COIN_AMOUNT*rate);


        return ResUtils.ok(busiId);
    }

    private HttpChatHistoryService httpChatHistoryService;

    @PostMapping("refuse")
    public R<Long> refuse(@RequestParam("applyId") Integer applyId, @RequestParam Long pageUserId, @SessionAttribute Long userId) {
        //TODO 拒绝的话，退一半，佣金？

        LocalDateTime now = LocalDateTime.now();
        long busiId = busiController.insert(new Busi().setBusiTime(now).setBusiTypeId(BusiType.REFUSE_DO_FRIEND.getIndex()));

        //2.写拒绝记录到表中 TODO，可增加拒绝意见
        Integer textId = 1;
        httpUserApplyRefuseService.add(applyId, textId);
        httpUserApplyTradeService.set(applyId, newArrayList(busiId));
        //退一半
        doTaskApi.doTaskGetCoin(userId, busiId, -(long) CoinConstant.REJECT_COIN_AMOUNT);

        return ResUtils.ok(busiId);
    }

    @PostMapping("apply-id-to-send-apply-content")
    public R<Map<Integer, SendApplyContent>> applyIdToSendApplyContent(@RequestBody Set<Integer> applyIds) {

        //1.同意
        Map<Integer, LocalDateTime> applyIdToAgreeTimeMap = httpUserApplyAgreeService.applyIdToAgreeTime(applyIds);
        //2.拒绝
        Map<Integer, LocalDateTime> applyIdToRefuseTimeMap = httpUserApplyRefuseService.applyIdToRefuseTime(applyIds);
        //3.过期
        Map<Integer, LocalDateTime> applyIdToApplyTimeMap = httpUserApplyService.applyIdToApplyTime(applyIds);
        //4.查看
        Map<Integer, LocalDateTime> applyIdToLookTimeMap = httpUserApplyLookService.applyIdToLookTime(applyIds);

        //5.无
        LocalDateTime now = LocalDateTime.now();

        Map<Integer, Integer> applyIdToTextIdMap = httpUserApplyService.applyIdToTextId(applyIds);
        Map<Integer, String> textIdToTextMap = httpTextService.textIdToText(new HashSet<>(applyIdToTextIdMap.values()));

        Map<Integer, SendApplyContent> result = applyIds
                .stream().parallel()
                .map(applyId -> {
                    LocalDateTime agreeTime = applyIdToAgreeTimeMap.get(applyId);
                    LocalDateTime refuseTime = applyIdToRefuseTimeMap.get(applyId);
                    LocalDateTime applyTime = applyIdToApplyTimeMap.get(applyId);
                    LocalDateTime lookTime = applyIdToLookTimeMap.get(applyId);
                    ApplyStatus applyStatus = ChatApplyUtils.toApplyStatus(agreeTime, refuseTime, applyTime, lookTime, now);
                    Integer textId = applyIdToTextIdMap.get(applyId);
                    String text = textIdToTextMap.get(textId);
                    SendApplyContent sendApplyContent = new SendApplyContent(applyStatus.getIndex(), text, agreeTime, refuseTime, lookTime, applyTime);
                    return Pair.of(applyId, sendApplyContent);
                }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        return ResUtils.ok(result);
    }

    @PostMapping("apply-id-to-rec-apply-content")
    public R<Map<Integer, RecApplyContent>> applyIdToRecApplyContent(@RequestBody Set<Integer> applyIds) {

        //1.同意
        Map<Integer, LocalDateTime> applyIdToAgreeTimeMap = httpUserApplyAgreeService.applyIdToAgreeTime(applyIds);
        //2.拒绝
        Map<Integer, LocalDateTime> applyIdToRefuseTimeMap = httpUserApplyRefuseService.applyIdToRefuseTime(applyIds);
        //3.过期
        Map<Integer, LocalDateTime> applyIdToApplyTimeMap = httpUserApplyService.applyIdToApplyTime(applyIds);
        //4.查看
        Map<Integer, LocalDateTime> applyIdToLookTimeMap = httpUserApplyLookService.applyIdToLookTime(applyIds);

        //5.无
        LocalDateTime now = LocalDateTime.now();

        Map<Integer, Integer> applyIdToTextIdMap = httpUserApplyService.applyIdToTextId(applyIds);
        Map<Integer, String> textIdToTextMap = httpTextService.textIdToText(new HashSet<>(applyIdToTextIdMap.values()));

        Map<Integer, RecApplyContent> result = applyIds
                .stream().parallel()
                .map(applyId -> {
                    LocalDateTime agreeTime = applyIdToAgreeTimeMap.get(applyId);
                    LocalDateTime refuseTime = applyIdToRefuseTimeMap.get(applyId);
                    LocalDateTime applyTime = applyIdToApplyTimeMap.get(applyId);
                    LocalDateTime lookTime = applyIdToLookTimeMap.get(applyId);
                    ApplyStatus applyStatus = ChatApplyUtils.toApplyStatus(agreeTime, refuseTime, applyTime, lookTime, now);
                    Integer textId = applyIdToTextIdMap.get(applyId);
                    String text = textIdToTextMap.get(textId);
                    RecApplyContent recApplyContent = new RecApplyContent(applyStatus.getIndex(), text, agreeTime, refuseTime, lookTime, applyTime);
                    return Pair.of(applyId, recApplyContent);
                }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        return ResUtils.ok(result);
    }

}
