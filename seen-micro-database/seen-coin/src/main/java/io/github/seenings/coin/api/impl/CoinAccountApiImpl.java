package io.github.seenings.coin.api.impl;

import com.songchi.seen.account.service.CoinAccountService;
import com.songchi.seen.account.service.CoinAccountUserService;
import com.songchi.seen.coin.enumeration.AccountType;
import io.github.seenings.coin.api.CoinAccountApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 玫瑰币账户设置
 */
@Slf4j
@RestController
@AllArgsConstructor
public class CoinAccountApiImpl implements CoinAccountApi {
    /**
     * 币账户
     */
    private CoinAccountService coinAccountService;

    /**
     * 用户的账户
     */
    private CoinAccountUserService coinAccountUserService;

    /**
     * 初始化账户
     *
     * @param userId 用户ID
     * @return 账户ID
     */
    @Override
    public Long initAccount(Long userId) {
        Long accountId = coinAccountService.createAccount(AccountType.USER);
        coinAccountUserService.set(accountId, userId);
        return accountId;
    }

    /**
     * 根据用户ID获取账户ID
     *
     * @param userIds 用户ID
     * @return 用户ID对应账户ID
     */
    @Override
    public Map<Long, Long> userIdToAccountId(Set<Long> userIds) {
        Map<Long, Set<Long>> userIdToAccountIdMap = coinAccountUserService.userIdToAccountId(userIds);

        Set<Long> accountIds = userIdToAccountIdMap.values().stream().parallel().flatMap(Collection::stream).collect(Collectors.toSet());
        Map<Long, AccountType> accountIdToAccountTypeMap = coinAccountService.accountIdToAccountType(accountIds);
        // 获取账户
        return userIds.stream().parallel().map(n -> {
            Set<Long> resultAccountIds = userIdToAccountIdMap.get(n);
            Long accountId = resultAccountIds.stream().parallel().filter(l -> accountIdToAccountTypeMap.get(l) == AccountType.USER).findFirst().orElse(null);
            if (accountId == null) {
                log.error("用户的账户不存在,用户ID:{}", n);
                return null;
            }
            return Map.entry(n, accountId);
        }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * 根据系统账户类型获取账户ID
     *
     * @param accountTypes 账户类型
     * @return 账户类型对应账户ID
     */
    @Override
    public Map<AccountType, Long> sysAccountTypeToAccountId(Set<AccountType> accountTypes) {
        Set<Integer> accountTypeIds = accountTypes.stream().parallel().map(AccountType::getIndex).collect(Collectors.toSet());

        Map<Integer, List<Long>> accountTypeIdToAccountIdMap = coinAccountService.accountTypeToAccountId(accountTypeIds);
        return accountTypes.stream().parallel().map(n -> {
            int accountTypeId = n.getIndex();
            List<Long> integers = accountTypeIdToAccountIdMap.get(accountTypeId);
            if (integers != null && !integers.isEmpty()) {
                return Map.entry(n, integers.getFirst());
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}
