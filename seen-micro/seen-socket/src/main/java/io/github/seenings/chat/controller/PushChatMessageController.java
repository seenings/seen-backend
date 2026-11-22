package io.github.seenings.chat.controller;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

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
    public boolean send(Integer id) {
        // 如果map中不含有队列，则新建队列
        Long otherUserId = httpChatHistoryService.idToToUserId(Set.of(id)).get(id);
        log.info("发送消息，聊天记录ID：{}，接收用户ID：{}", id, otherUserId);
        Queue<Integer> historyIdQueue = TwoChatSseController.USER_ID_TO_HISTORY_ID_MAP.computeIfAbsent(otherUserId,
                (_) -> new LinkedBlockingQueue<>());
        historyIdQueue.add(id);
        return httpChatHistoryService.setSent(id);

    }
}
