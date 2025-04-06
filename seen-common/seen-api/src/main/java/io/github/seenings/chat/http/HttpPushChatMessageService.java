package io.github.seenings.chat.http;

import static io.github.seenings.sys.constant.SeenConstant.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.seenings.sys.constant.ServiceNameConstant;

/**
 * HttpPushChatMessageService
 *
 */

@FeignClient(name = ServiceNameConstant.SERVICE_SEEN_SOCKET, path = FEIGN_VERSION
        + "chat/push-chat-message", contextId = "HttpPushChatMessageService")
public interface HttpPushChatMessageService {
    @PostMapping("send")
    boolean send(@RequestParam("id") Integer id);
}
