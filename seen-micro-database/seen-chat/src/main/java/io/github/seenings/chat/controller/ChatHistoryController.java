package io.github.seenings.chat.controller;

import cn.hutool.core.lang.Pair;
import io.github.seenings.article.enumeration.ContentType;
import io.github.seenings.chat.http.HttpChatHistoryService;
import io.github.seenings.chat.model.ChatContentAndTime;
import io.github.seenings.chat.service.ChatHistoryService;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.sys.util.ListUtils;
import io.github.seenings.sys.constant.SeenConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ChatHistoryController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Slf4j
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "chat/chat-history")
public class ChatHistoryController implements HttpChatHistoryService {

    @Resource
    private ChatHistoryService chatHistoryService;


    @Override
    @PostMapping("add")
    public Integer add(@RequestParam("contentType") ContentType contentType, @RequestParam("contentId") Integer contentId, @RequestParam("fromUserId") Long fromUserId, @RequestParam("toUserId") Long toUserId, @RequestParam("sent") Boolean sent, @RequestParam("sendTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime sendTime) {
        return chatHistoryService.add(contentType, contentId, fromUserId, toUserId, sent, sendTime);
    }

    /**
     * 根据页面用户ID和自己的用户ID获取聊天记录ID
     *
     * @param pageUserId 页面用户ID
     * @param selfUserId 自己用户ID
     * @return 聊天记录ID
     */
    @Override
    @PostMapping("page-user-id-to-history-id")
    public List<Integer> pageUserIdToHistoryId(@RequestParam("pageUserId") Long pageUserId, @RequestParam("selfUserId") Long selfUserId) {
        List<Integer> toIds = chatHistoryService.fromUserIdToId(pageUserId, selfUserId, 1, 10);
        List<Integer> fromIds = chatHistoryService.fromUserIdToId(selfUserId, pageUserId, 1, 10);
        List<Integer> ids = ListUtils.union(toIds, fromIds);
        Map<Integer, LocalDateTime> idToSendTimeMap = chatHistoryService.idToSendTime(new HashSet<>(ids));
        //按发送时间排序
        return idToSendTimeMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).toList();
    }

    /**
     * 根据ID获取聊天内容和时间
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应聊天内容和时间
     */
    @Override
    @PostMapping("id-to-chat-content-and-time")
    public Map<Integer, ChatContentAndTime> idToChatContentAndTime(@RequestBody Set<Integer> ids) {
        return chatHistoryService.idToChatContentAndTime(ids);
    }

    @Override
    @PostMapping("user-id-to-chat-content-and-time")
    public Map<Long, ChatContentAndTime> userIdToChatContentAndTime(@RequestBody Set<Long> userIds, @RequestParam("toUserId") Long toUserId) {
        Map<Long, List<Integer>> fromUserIdToChatHistoryIdMap = userIds.stream().parallel().map(fromUserId -> Pair.of(fromUserId, chatHistoryService.fromUserIdToId(fromUserId, toUserId, 1, 1))).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        Set<Integer> ids = fromUserIdToChatHistoryIdMap.values().stream().parallel().flatMap(Collection::stream).collect(Collectors.toSet());
        Map<Integer, ChatContentAndTime> idToChatContentAndTimeMap = chatHistoryService.idToChatContentAndTime(ids);
        return userIds.stream().parallel().map(n -> {
            List<Integer> resultChatHistoryIds = fromUserIdToChatHistoryIdMap.get(n);
            if (CollUtil.isNotEmpty(resultChatHistoryIds) && resultChatHistoryIds.size() == 1) {
                Integer id = CollUtil.first(resultChatHistoryIds);
                ChatContentAndTime chatContentAndTime = idToChatContentAndTimeMap.get(id);
                return Pair.of(n, chatContentAndTime);
            } else {
                String msg = String.format("聊天记录条数不对，个数：%s。", CollUtil.size(resultChatHistoryIds));
                log.error(msg);
                return null;
            }
        }).filter(Objects::nonNull).filter(n -> n.getValue() != null).collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }


    @Override
    @PostMapping("set-sent")
    public boolean setSent(@RequestParam("id") Integer id) {
        return chatHistoryService.setSent(id);
    }


    /**
     * 根据聊天记录ID获取发送方ID
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应发送方ID
     */
    @Override
    @PostMapping("id-to-from-user-id")
    public Map<Integer, Long> idToFromUserId(@RequestBody Set<Integer> ids) {
        return chatHistoryService.idToFromUserId(ids);
    }


    /**
     * 根据聊天记录ID获取是否发出
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应是否发出
     */
    @Override
    @PostMapping("id-to-is-sent")
    public Map<Integer, Boolean> idToIsSent(@RequestBody Set<Integer> ids) {
        return chatHistoryService.idToIsSent(ids);
    }

    /**
     * 根据聊天记录ID获取接收方ID
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应接收方ID
     */
    @Override
    @PostMapping("id-to-to-user-id")
    public Map<Integer, Long> idToToUserId(@RequestBody Set<Integer> ids) {
        return chatHistoryService.idToToUserId(ids);
    }
}
