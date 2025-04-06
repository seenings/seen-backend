package io.github.seenings.account.http;

import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpCoinAccountService
 *
 * @author chixuehui
 * @since 2023-03-05
 */

@FeignClient(name = ServiceNameConstant.SERVICE_SEEN_COIN, path = FEIGN_VERSION + "account/coin-account", contextId = "HttpCoinAccountService")
public interface HttpCoinAccountService {
    @PostMapping("init-account")
    void initAccount(@RequestParam("userId") Long userId);

}
