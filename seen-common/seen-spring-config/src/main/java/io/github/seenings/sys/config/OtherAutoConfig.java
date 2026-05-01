package io.github.seenings.sys.config;

import io.github.seenings.address.http.HttpCityService;
import io.github.seenings.address.http.HttpProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 其他的配置
 */
@AutoConfiguration
@AllArgsConstructor
public class OtherAutoConfig {
    /*
     * 其他的代理
     *
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenOther;

    /**
     * 城市
     *
     * @return 接口
     */
    @Bean
    public HttpCityService httpCityService() {
        return httpServiceProxyFactoryBySeenOther.createClient(HttpCityService.class);
    }

    /**
     * 省份
     *
     * @return 接口
     */
    @Bean
    public HttpProvinceService httpProvinceService() {
        return httpServiceProxyFactoryBySeenOther.createClient(HttpProvinceService.class);
    }


}
