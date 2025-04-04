package com.songchi.seen.account.http;

import com.songchi.seen.coin.enumeration.AccountType;
import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpCoinAccountService
 *
 * @author chixuehui
 * @since 2023-03-05
 */

@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_COIN,
        path = FEIGN_VERSION + "account/coin-account",
        contextId = "HttpCoinAccountService")
public interface HttpCoinAccountService {
    @PostMapping("init-account")
    void initAccount(@RequestParam("userId") Long userId);

    @PostMapping("user-id-to-account-id")
    Map<Long, Long> userIdToAccountId(@RequestBody Set<Long> userIds, @RequestParam("accountType") AccountType accountType);
}
