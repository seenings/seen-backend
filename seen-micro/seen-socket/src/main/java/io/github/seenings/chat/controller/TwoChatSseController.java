package io.github.seenings.chat.controller;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import io.github.seenings.sys.constant.PublicConstant;
import lombok.AllArgsConstructor;

/**
 * 一对一
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.SSE_VERSION + "chat/two-chat")
public class TwoChatSseController {

    // 队列，后续使用redis
    public static Map<Long, BlockingQueue<Integer>> USER_ID_TO_HISTORY_ID_MAP = new ConcurrentHashMap<>();

    /// EXECUTOR_SERVICE
    public static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    /**
     * 接收消息
     *
     * @param userId 用户
     * @return 返回sse
     */
    @GetMapping(value = "receive", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter receive(@SessionAttribute Long userId) {
        log.info("开启接收消息会话，用户ID：{}", userId);
        SseEmitter sseEmitter = new SseEmitter();
        AtomicBoolean running = new AtomicBoolean(true);

        // 回调：客户端断开 / 超时 / 异常，停止循环
        sseEmitter.onCompletion(() -> {
            log.info("sse 客户端断开, userId:{}", userId);
            running.set(false);
        });
        sseEmitter.onTimeout(() -> {
            log.info("sse 客户端超时, userId:{}", userId);
            running.set(false);
            sseEmitter.complete();
        });
        sseEmitter.onError(e -> {
            log.error("sse 客户端异常, userId:{}", userId, e);
            running.set(false);
            sseEmitter.complete();
        });
        BlockingQueue<Integer> queue = USER_ID_TO_HISTORY_ID_MAP.computeIfAbsent(userId, k -> new LinkedBlockingQueue<>());
        EXECUTOR_SERVICE.execute(() -> {
            try {
                while (running.get() && !Thread.currentThread().isInterrupted()) {
                    Integer historyId = queue.poll(100L, TimeUnit.MILLISECONDS);
                    if (historyId == null) {
                        continue;
                    }
                    log.info("接收消息，用户ID：{}，聊天记录ID：{}", userId, historyId);
                    sseEmitter.send(SseEmitter.event().data(historyId));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("sse 线程中断 userId:{}", userId, e);
            } catch (Exception e) {
                log.error("获取聊天记录异常, userId:{}", userId, e);
            }
            // 正常业务一般不在这 complete，由外部关闭逻辑控制
        });
        return sseEmitter;
    }

}
