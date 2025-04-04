package com.songchi.seen.info.controller;

import static com.google.common.collect.Lists.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.songchi.seen.apply.http.HttpUserApplyAgreeService;
import com.songchi.seen.apply.http.HttpUserApplyLookService;
import com.songchi.seen.apply.http.HttpUserApplyRefuseService;
import com.songchi.seen.apply.http.HttpUserApplyService;
import com.songchi.seen.apply.http.HttpUserApplyTradeService;
import com.songchi.seen.article.enumeration.ContentType;
import com.songchi.seen.chat.constant.ChatConstant;
import com.songchi.seen.chat.enumeration.ApplyStatus;
import com.songchi.seen.chat.http.HttpChatHistoryService;
import com.songchi.seen.chat.http.HttpChatUserService;
import com.songchi.seen.chat.utils.ChatApplyUtils;
import com.songchi.seen.coin.enumeration.TradeType;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.info.model.RecApplyContent;
import com.songchi.seen.info.model.SendApplyContent;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.text.http.HttpTextService;
import com.songchi.seen.trade.http.HttpCoinTradeService;

import cn.hutool.core.lang.Pair;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * FriendController
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Slf4j
@RestController
@RequestMapping(PublicConstant.REST + "user/friend")
public class FriendController {

    @Resource
    private HttpChatUserService httpChatUserService;

    @Resource
    private HttpCoinTradeService httpCoinTradeService;

    @Resource
    private HttpUserApplyService httpUserApplyService;

    @Resource
    private HttpUserApplyTradeService httpUserApplyTradeService;

    @Resource
    private HttpTextService httpTextService;

    @PostMapping("apply-friend")
    public R<Boolean> applyFriend(
            @SessionAttribute Long userId, @RequestParam("appliedUserId") Long appliedUserId) {

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
        String description = TradeType.APPLY_DO_FRIEND.getLabel();
        Set<Integer> tradeIds = httpCoinTradeService.checkEnoughAndFreeze(userId, ChatConstant.APPLY_SPEND_COIN_AMOUNT,
                TradeType.APPLY_DO_FRIEND, description);
        if (CollUtils.isEmpty(tradeIds)) {
            // 没有足够的虚拟币
            String msg = String.format("没有足够的虚拟币，用户ID是：%s，朋友ID是：%s。", userId, appliedUserId);
            log.warn(msg);
            return ResUtils.ok(false, msg);
        }
        // 3.添加一条好友申请记录
        // 3.1 添加好友申请文本
        String text = "很高兴认识你";
        Integer textId = httpTextService.saveAndReturnId(text);
        // 3.2 添加好友申请记录
        Integer applyId = httpUserApplyService.set(userId, textId, appliedUserId);
        httpUserApplyTradeService.set(applyId, new ArrayList<>(tradeIds));

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
        String description = TradeType.APPLY_DO_FRIEND.getLabel();
        Set<Integer> tradeIds = httpCoinTradeService.checkEnoughAndFreeze(userId, ChatConstant.APPLY_SPEND_COIN_AMOUNT * 2, TradeType.APPLY_DO_FRIEND, description);
        if (CollUtils.isEmpty(tradeIds)) {
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
        Integer applyId = httpUserApplyService.set(userId, textId, pageUserId);
        httpUserApplyTradeService.set(applyId, new ArrayList<>(tradeIds));

        // TODO，生成一条通知消息
        return ResUtils.ok(true);
    }

    @Resource
    private HttpUserApplyAgreeService httpUserApplyAgreeService;

    @Resource
    private HttpUserApplyLookService httpUserApplyLookService;
    @Resource
    private HttpUserApplyRefuseService httpUserApplyRefuseService;

    @PostMapping("look")
    public R<Integer> look(
            @RequestParam("applyId") Integer applyId) {
        //1.收集用户查看记录
        Integer lookId = httpUserApplyLookService.set(applyId, LocalDateTime.now());
        return ResUtils.ok(lookId);
    }

    @PostMapping("agree")
    public R<Integer> agree(@SessionAttribute Long userId,
                            @RequestParam("applyId") Integer applyId, @RequestParam("pageUserId") Long pageUserId) {

        //1.冻结虚拟币转到系统账户中
        Integer tradeId = httpCoinTradeService.freezeToSysUse(pageUserId, ChatConstant.APPLY_SPEND_COIN_AMOUNT, TradeType.AGREE_DO_FRIEND, "同意");
        //2.写同意记录到表中
        httpUserApplyAgreeService.set(applyId, LocalDateTime.now());
        httpUserApplyTradeService.set(applyId, Collections.singletonList(tradeId));
        //3.添加用户到好友列表
        httpChatUserService.set(userId, pageUserId);
        httpChatUserService.set(pageUserId, userId);
        //4.添加聊天消息
        LocalDateTime applyTime = httpUserApplyService.applyIdToApplyTime(Collections.singleton(applyId)).get(applyId);
        Integer applyTextId = httpUserApplyService.applyIdToTextId(Collections.singleton(applyId)).get(applyId);
        httpChatHistoryService.add(  ContentType.TEXT , applyTextId,
             userId,   pageUserId,   false,applyTime  );
        return ResUtils.ok(tradeId);
    }

    @Resource
    private HttpChatHistoryService httpChatHistoryService;

    @PostMapping("refuse")
    public R<Integer> refuse(@RequestParam("applyId") Integer applyId, @RequestParam Long pageUserId) {

        //1.冻结虚拟币转到申请人的账户中
        //2.冻结虚拟币转到系统的账户中
        Integer backTradeId = httpCoinTradeService.freezeToTemporary(pageUserId, ChatConstant.APPLY_SPEND_COIN_AMOUNT - ChatConstant.REJECT_COIN_AMOUNT, TradeType.REFUSE_DO_FRIEND, "拒绝");
        Integer tradeId = httpCoinTradeService.freezeToSysUse(pageUserId, ChatConstant.REJECT_COIN_AMOUNT, TradeType.REFUSE_DO_FRIEND, "拒绝");
        //2.写拒绝记录到表中
        Integer textId = 1;
        httpUserApplyRefuseService.add(applyId, textId);
        httpUserApplyTradeService.set(applyId, newArrayList(tradeId, backTradeId));
        return ResUtils.ok(tradeId);
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
