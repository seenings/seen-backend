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
    public void initAccount(@RequestParam("userId") Integer userId) {
        Integer userForeverAccountId = coinAccountService.createAccount("",
                AccountType.USER_FOREVER);
        Integer userTemporaryAccountId = coinAccountService.createAccount("",
                AccountType.USER_TEMPORARY);
        Integer userFreezeAccountId = coinAccountService.createAccount("",
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
    public Map<Integer, Integer> userIdToAccountId(@RequestBody Set<Integer> userIds, @RequestParam("accountType") AccountType accountType) {
        Map<Integer, Set<Integer>> userIdToAccountIdMap = coinAccountUserService
                .userIdToAccountId(userIds);

        Set<Integer> accountIds = userIdToAccountIdMap.values().stream().parallel()
                .flatMap(Collection::stream).collect(Collectors.toSet());
        Map<Integer, AccountType> accountIdToAccountTypeMap = coinAccountService.accountIdToAccountType(accountIds);
        // 获取账户
        return userIds.stream().parallel()
                .map(n -> {
                    Set<Integer> resultAccountIds = userIdToAccountIdMap.get(n);
                    Integer accountId = resultAccountIds.stream().parallel()
                            .filter(l -> accountIdToAccountTypeMap.get(l) == accountType)
                            .findFirst().orElse(null);
                    return Pair.of(n, accountId);
                }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
