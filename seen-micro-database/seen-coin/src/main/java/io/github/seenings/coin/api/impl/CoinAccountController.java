package io.github.seenings.coin.api.impl;

import cn.hutool.core.lang.Pair;
import com.songchi.seen.account.http.HttpCoinAccountService;
import com.songchi.seen.account.service.CoinAccountBalanceService;
import com.songchi.seen.account.service.CoinAccountService;
import com.songchi.seen.account.service.CoinAccountUserService;
import com.songchi.seen.coin.enumeration.AccountType;
import com.songchi.seen.sys.constant.SeenConstant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CoinAccountController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RequestMapping(SeenConstant.FEIGN_VERSION + "account/coin-account")
@RestController
public class CoinAccountController implements HttpCoinAccountService {

    @Resource
    private CoinAccountService coinAccountService;
    @Resource
    private CoinAccountUserService coinAccountUserService;
    @Resource
    private CoinAccountBalanceService coinAccountBalanceService;

    @Override
    @PostMapping("init-account")
    public void initAccount(@RequestParam("userId") Long userId) {
        Long userForeverAccountId = coinAccountService.createAccount(
                AccountType.USER_FOREVER);
        Long userTemporaryAccountId = coinAccountService.createAccount(
                AccountType.USER_TEMPORARY);
        Long userFreezeAccountId = coinAccountService.createAccount(
                AccountType.USER_FREEZE);

        coinAccountUserService.set(userForeverAccountId, userId);
        coinAccountUserService.set(userTemporaryAccountId, userId);
        coinAccountUserService.set(userFreezeAccountId, userId);

        coinAccountBalanceService.set(userForeverAccountId, 0);
        coinAccountBalanceService.set(userTemporaryAccountId, 0);
        coinAccountBalanceService.set(userFreezeAccountId, 0);
    }

    @Override
    @PostMapping("user-id-to-account-id")
    public Map<Long, Long> userIdToAccountId(@RequestBody Set<Long> userIds, @RequestParam("accountType") AccountType accountType) {
        Map<Long, Set<Long>> userIdToAccountIdMap = coinAccountUserService
                .userIdToAccountId(userIds);

        Set<Long> accountIds = userIdToAccountIdMap.values().stream().parallel()
                .flatMap(Collection::stream).collect(Collectors.toSet());
        Map<Long, AccountType> accountIdToAccountTypeMap = coinAccountService.accountIdToAccountType(accountIds);
        // 获取账户
        return userIds.stream().parallel()
                .map(n -> {
                    Set<Long> resultAccountIds = userIdToAccountIdMap.get(n);
                    Long accountId = resultAccountIds.stream().parallel()
                            .filter(l -> accountIdToAccountTypeMap.get(l) == accountType)
                            .findFirst().orElse(null);
                    if (accountId == null) {
                        return null;
                    }
                    return Map.entry(n, accountId);
                }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
