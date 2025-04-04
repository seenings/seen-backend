package com.songchi.seen.chat.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.songchi.seen.chat.http.HttpChatHistoryService;
import com.songchi.seen.sys.constant.PublicConstant;

import lombok.AllArgsConstructor;

/**
 * 一对一
 */
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.SSE_VERSION + "chat/two-chat")
public class TwoChatSseController {

    // 队列，后续使用redis
    public static Map<Long, Queue<Integer>> userIdToHistoryIdMap = new ConcurrentHashMap<>();

    private static final ExecutorService ONE_TO_ONE_EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    /**
     * 历史聊天记录
     */
    private HttpChatHistoryService httpChatHistoryService;

    /**
     * 发送消息
     *
     * @param userId    用户
     * @param historyId 历史ID
     * @return 是否发送成功
     */
    @PostMapping("send")
    public boolean send(@SessionAttribute Long userId, @RequestBody Integer historyId) {
        System.out.println("发送消息" + userId + "：" + historyId);
        // 如果map中不含有队列，则新建队列

        Long otherUserId = httpChatHistoryService.idToToUserId(Set.of(historyId)).get(historyId);
        Queue<Integer> historyIdQueue = userIdToHistoryIdMap.computeIfAbsent(otherUserId, (key) -> new ConcurrentLinkedQueue<>());
        return historyIdQueue.add(historyId);
    }

    /**
     * 接收消息
     *
     * @param userId 用户
     * @return 返回sse
     */
    @GetMapping(value = "receive", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter receive(@SessionAttribute Long userId) {
        System.out.println(userId);
        SseEmitter sseEmitter = new SseEmitter();
        ONE_TO_ONE_EXECUTOR_SERVICE.execute(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    var queue = userIdToHistoryIdMap.get(userId);
                    if (queue != null) {
                        // 优化
                        var historyId = queue.poll();
                        if (historyId != null) {
                            sseEmitter.send(SseEmitter.event().data(historyId));
                        }
                    }
                    Thread.sleep(100L);
                }
            } catch (IOException | InterruptedException e) {
                sseEmitter.completeWithError(e);
            } finally {
                sseEmitter.complete();
            }
        });
        return sseEmitter;
    }

}
