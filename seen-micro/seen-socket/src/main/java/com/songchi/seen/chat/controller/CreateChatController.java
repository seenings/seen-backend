package com.songchi.seen.chat.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.songchi.seen.chat.model.NeedRecChatMessage;
import com.songchi.seen.sys.constant.PublicConstant;

@RestController
@RequestMapping(PublicConstant.SSE_VERSION + "chat/create-chat")
public class CreateChatController {

    // 队列，后续使用redis
    public static Map<Long, Queue<NeedRecChatMessage>> userIdToNeedRecChatMessageMap = new ConcurrentHashMap<>();

    private static final ExecutorService ONE_TO_ONE_EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    @PostMapping("send-message")
    public boolean sendMessage(@SessionAttribute Long userId) {
        System.out.println("发送消息" + userId);
        // 如果map中不含有队列，则新建队列
        Queue<NeedRecChatMessage> needRecChatMessageQueue = userIdToNeedRecChatMessageMap.computeIfAbsent(userId, (key) -> new ConcurrentLinkedQueue<>());
        var needRecChatMessage = new NeedRecChatMessage(100000001L, 1, 2, LocalDateTime.now());
        needRecChatMessageQueue.add(needRecChatMessage);
        System.out.println(needRecChatMessage);
        return true;
    }

    /**
     * 一对一
     *
     * @param userId 用户
     * @return 返回sse
     */
    @GetMapping(value = "one-to-one", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter OneToOne(@SessionAttribute Long userId) {
        System.out.println(userId);
        SseEmitter sseEmitter = new SseEmitter();
        ONE_TO_ONE_EXECUTOR_SERVICE.execute(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    var needRecChatMessageQueue = userIdToNeedRecChatMessageMap.get(userId);
                    if (needRecChatMessageQueue != null) {
                        var needRecChatMessage = needRecChatMessageQueue.poll();
                        if (needRecChatMessage != null) {
                            sseEmitter.send(SseEmitter.event().data(needRecChatMessage));
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

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamEvents() {
        SseEmitter emitter = new SseEmitter();
        executor.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    emitter.send(SseEmitter.event().data("Event " + i));
                    Thread.sleep(1000); // sleep for 1 second
                }
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            } finally {
                emitter.complete();
            }

        });

        return emitter;
    }
}
