package io.github.seenings.sys.config;

import io.github.seenings.chat.http.HttpChatHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 聊天
 */
@Slf4j
@AutoConfiguration
@AllArgsConstructor
public class ChatAutoConfig {
    /**
     * 聊天
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenChat;

    /**
     * 聊天历史
     *
     * @return 接口实例
     */
    @Bean
    public HttpChatHistoryService httpChatHistoryService() {
        return httpServiceProxyFactoryBySeenChat.createClient(HttpChatHistoryService.class);
    }

}
