package com.songchi.seen.chat.controller;

import static com.songchi.seen.sys.constant.SeenConstant.*;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.songchi.seen.chat.http.HttpChatHistoryService;
import com.songchi.seen.chat.http.HttpPushChatMessageService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * PushChatMessageController
 *
 */
@Slf4j
@RestController
@RequestMapping(FEIGN_VERSION + "chat/push-chat-message")
public class PushChatMessageController implements HttpPushChatMessageService {

    @Resource
    private HttpChatHistoryService httpChatHistoryService;

    @Override
    @PostMapping("send")
    public boolean send(@RequestParam("id") Integer id) {
        Integer historyId = id;
        log.warn("发送消息：" + historyId);
        // 如果map中不含有队列，则新建队列

        Long otherUserId = httpChatHistoryService.idToToUserId(Set.of(historyId)).get(historyId);
        Queue<Integer> historyIdQueue = TwoChatSseController.userIdToHistoryIdMap.computeIfAbsent(otherUserId,
                (key) -> {
                    return new ConcurrentLinkedQueue<>();
                });
        historyIdQueue.add(historyId);
        return httpChatHistoryService.setSent(id);

    }
}
