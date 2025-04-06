package io.github.seenings.coin.api;


import io.github.seenings.coin.enumeration.AccountType;
import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * 玫瑰币账户设置
 */
@HttpExchange(SeenConstant.SEEN_SMALL + "/coin/coin/coin-account")
public interface CoinAccountApi {
    /**
     * 初始化账户
     *
     * @param userId 用户ID
     * @return 账户ID
     */
    @PostExchange("init-account")
    Long initAccount(@RequestParam("userId") Long userId);

    /**
     * 根据用户ID获取账户ID
     *
     * @param userIds 用户ID
     * @return 用户ID对应账户ID
     */
    @PostExchange("user-id-to-account-id")
    Map<Long, Long> userIdToAccountId(@RequestBody Set<Long> userIds);

    /**
     * 根据系统账户类型获取账户ID
     *
     * @param accountTypes 账户类型
     * @return 账户类型对应账户ID
     */
    @PostExchange("sys-account-type-to-account-id")
    Map<AccountType, Long> sysAccountTypeToAccountId(@RequestBody Set<AccountType> accountTypes);
}
