package io.github.seenings.chat.controller;

import static io.github.seenings.sys.constant.SeenConstant.*;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.seenings.chat.http.HttpChatHistoryService;
import io.github.seenings.chat.http.HttpPushChatMessageService;

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
        log.warn("发送消息：" + id);
        // 如果map中不含有队列，则新建队列

        Long otherUserId = httpChatHistoryService.idToToUserId(Set.of(id)).get(id);
        Queue<Integer> historyIdQueue = TwoChatSseController.userIdToHistoryIdMap.computeIfAbsent(otherUserId,
                (key) -> new ConcurrentLinkedQueue<>());
        historyIdQueue.add(id);
        return httpChatHistoryService.setSent(id);

    }
}
