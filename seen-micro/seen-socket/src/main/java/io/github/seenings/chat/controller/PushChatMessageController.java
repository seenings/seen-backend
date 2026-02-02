package io.github.seenings.chat.controller;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import io.github.seenings.chat.http.HttpChatHistoryService;
import io.github.seenings.chat.http.HttpPushChatMessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * PushChatMessageController
 *
 */
@Slf4j
@RestController
@AllArgsConstructor
public class PushChatMessageController implements HttpPushChatMessageService {

    private HttpChatHistoryService httpChatHistoryService;

    @Override
    public boolean send( Integer id) {
        log.warn("发送消息：{}", id);
        // 如果map中不含有队列，则新建队列

        Long otherUserId = httpChatHistoryService.idToToUserId(Set.of(id)).get(id);
        Queue<Integer> historyIdQueue = TwoChatSseController.userIdToHistoryIdMap.computeIfAbsent(otherUserId,
                (key) -> new ConcurrentLinkedQueue<>());
        historyIdQueue.add(id);
        return httpChatHistoryService.setSent(id);

    }
}
