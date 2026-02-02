package io.github.seenings.chat.http;

import static io.github.seenings.sys.constant.SeenConstant.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * HttpPushChatMessageService
 *
 */

@HttpExchange( FEIGN_VERSION
        + "chat/push-chat-message" )
public interface HttpPushChatMessageService {
    @PostExchange("send")
    boolean send(@RequestParam("id") Integer id);
}
