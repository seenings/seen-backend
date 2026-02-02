package io.github.seenings.sys.config;

import io.github.seenings.chat.http.HttpPushChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 计算的配置
 */
@AutoConfiguration
@AllArgsConstructor
public class SocketAutoConfig {
    /*
     * 套接字
     *
     * @return 代理
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenSocket;
    /**
     * 推送聊天消息
     *
     * @return 接口
     */
    @Bean
    public HttpPushChatMessageService httpPushChatMessageService() {
        return httpServiceProxyFactoryBySeenSocket.createClient(HttpPushChatMessageService.class);
    }
}
