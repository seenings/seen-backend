package io.github.seenings.sys.config;

import io.github.seenings.coin.api.CoinAccountApi;
import io.github.seenings.coin.api.CoinTradeApi;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 币的配置
 */
@AutoConfiguration
@AllArgsConstructor
public class CoinAutoConfig {
    /**
     * 币的代理
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenCoin;

    /**
     * 玫瑰币账户设置
     *
     * @return 玫瑰币账户设置
     */
    @Bean
    public CoinAccountApi coinAccountApi() {
        return httpServiceProxyFactoryBySeenCoin.createClient(CoinAccountApi.class);
    }

    /**
     * 玫瑰币交易
     *
     * @return 玫瑰币交易
     */
    @Bean
    public CoinTradeApi coinTradeApi() {
        return httpServiceProxyFactoryBySeenCoin.createClient(CoinTradeApi.class);
    }
}
