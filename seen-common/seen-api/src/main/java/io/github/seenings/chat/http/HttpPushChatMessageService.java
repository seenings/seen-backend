package io.github.seenings.chat.http;

import static io.github.seenings.sys.constant.SeenConstant.*;

import org.springframework.web.bind.annotation.RequestParam;

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
