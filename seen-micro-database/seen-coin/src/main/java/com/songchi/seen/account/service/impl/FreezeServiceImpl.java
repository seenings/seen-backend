package com.songchi.seen.account.service.impl;

import com.songchi.seen.account.http.HttpCoinAccountService;
import com.songchi.seen.account.service.CoinAccountBalanceService;
import com.songchi.seen.account.service.CoinAccountService;
import com.songchi.seen.account.service.CoinAccountUserService;
import com.songchi.seen.account.service.FreezeService;
import com.songchi.seen.coin.enumeration.AccountType;
import com.songchi.seen.coin.enumeration.TradeType;
import com.songchi.seen.trade.service.TradeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.hutool.core.collection.CollUtil.newHashSet;

/**
 * FreezeServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Service
public class FreezeServiceImpl implements FreezeService {

    @Resource
    private CoinAccountUserService coinAccountUserService;

    @Resource
    private CoinAccountService coinAccountService;

    @Resource
    private CoinAccountBalanceService coinAccountBalanceService;

    @Resource
    private HttpCoinAccountService httpCoinAccountService;


    /**
     * 冻结账户转给系统使用账户
     * @param userId    用户ID
     * @param coinMount 虚拟币数量
     * @param tradeType 交易类型
     * @param description   描述
     * @return  交易ID
     */
    @Override
    public Integer freezeToSysUse(Integer userId, int coinMount,
                                  TradeType tradeType, String description) {

        Integer freezeAccountId = httpCoinAccountService.userIdToAccountId(Collections.singleton(userId), AccountType.USER_FREEZE)
                .get(userId);
        int sysUseAccountTypeId =
                AccountType.SYS_USE.getIndex();
        List<Integer> accountIds = coinAccountService.accountTypeToAccountId(Collections.singleton(sysUseAccountTypeId))
                .get(sysUseAccountTypeId);
        Integer sysUseAccountId = accountIds.stream().findFirst().orElse(null);

        return tradeService.sysInTrade(sysUseAccountId, freezeAccountId, coinMount, tradeType, description);
    }
    @Override
    public Integer freezeToTemporary(Integer userId, int coinMount,
                                  TradeType tradeType, String description) {

        Integer freezeAccountId = httpCoinAccountService.userIdToAccountId(Collections.singleton(userId), AccountType.USER_FREEZE)
                .get(userId);
        Integer temporaryAccountId = httpCoinAccountService.userIdToAccountId(Collections.singleton(userId), AccountType.USER_TEMPORARY)
                .get(userId);

        return tradeService.trade(temporaryAccountId, freezeAccountId, coinMount, tradeType, description);
    }

    /**
     * 检查是否有足够的虚拟币
     *
     * @param userId    用户ID
     * @param coinMount 币的数量
     * @return 是否足够
     */
    @Override
    public Boolean checkEnough(Integer userId, int coinMount) {

        // 对用户账户进行锁定
        // 1.检查用户可用账户是否有足够的虚拟币
        Set<Integer> accountIds = coinAccountUserService
                .userIdToAccountId(Collections.singleton(userId))
                .get(userId);
        Map<Integer, AccountType> accountIdToAccountTypeMap = coinAccountService.accountIdToAccountType(accountIds);
        // 获取可用账户
        // 永久
        Integer foreverAccountId = accountIdToAccountTypeMap.entrySet().stream()
                .filter(n -> n.getValue() == AccountType.USER_FOREVER)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        // 临时
        Integer temporaryAccountId = accountIdToAccountTypeMap.entrySet().stream()
                .filter(n -> n.getValue() == AccountType.USER_TEMPORARY)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        // 获取账户的余额，如何保证一次访问
        Map<Integer, Integer> accountIdCoinAmountMap =
                coinAccountBalanceService.accountIdCoinAmount(accountIdToAccountTypeMap.keySet());
        // 检查是否有足够的金额
        Integer temporaryCoinAmount = accountIdCoinAmountMap.get(temporaryAccountId);
        Integer foreverCoinAmount = accountIdCoinAmountMap.get(foreverAccountId);
        return temporaryCoinAmount + foreverCoinAmount >= coinMount;
    }

    /**
     * 检查资金是否足够，如果够冻结
     *
     * @param userId    用户ID
     * @param coinMount 虚拟币个数
     * @return 冻结的交易ID，资金不够时返回空
     */
    @Override
    public Set<Integer> checkEnoughAndFreeze(Integer userId, int coinMount, TradeType tradeType, String description) {

        // 对用户账户进行锁定 //TODO，使用redis锁
        // 1.检查用户可用账户是否有足够的虚拟币
        Set<Integer> accountIds = coinAccountUserService
                .userIdToAccountId(Collections.singleton(userId))
                .get(userId);
        Map<Integer, AccountType> accountIdToAccountTypeMap = coinAccountService.accountIdToAccountType(accountIds);
        // 获取可用账户
        // 永久
        Integer foreverAccountId = accountIdToAccountTypeMap.entrySet().stream()
                .filter(n -> n.getValue() == AccountType.USER_FOREVER)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        // 临时
        Integer temporaryAccountId = accountIdToAccountTypeMap.entrySet().stream()
                .filter(n -> n.getValue() == AccountType.USER_TEMPORARY)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        // 冻结
        Integer freezeAccountId = accountIdToAccountTypeMap.entrySet().stream()
                .filter(n -> n.getValue() == AccountType.USER_FREEZE)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        // 获取账户的余额，如何保证一次访问
        Map<Integer, Integer> accountIdCoinAmountMap =
                coinAccountBalanceService.accountIdCoinAmount(accountIdToAccountTypeMap.keySet());
        // 检查是否有足够的金额
        Integer temporaryCoinAmount = accountIdCoinAmountMap.get(temporaryAccountId);
        Integer foreverCoinAmount = accountIdCoinAmountMap.get(foreverAccountId);
        int usableTotal = temporaryCoinAmount + foreverCoinAmount;
        if (usableTotal < coinMount) {
            // 不够，
            return null;
        }
        // 足够
        // 2.冻结指定的账户，附带冻结原因
        // 优先扣除临时账户，不够再扣永久账户
        // 余额扣除
        // 交易流水记录
        Set<Integer> tradeIds;
        if (temporaryCoinAmount >= coinMount) {
            // 临时够，冻结账户增加，临时账户减少

            Integer temporaryTradeId = tradeService.trade(freezeAccountId,
                    temporaryAccountId, coinMount, TradeType.APPLY_DO_FRIEND, description);
            tradeIds = newHashSet(temporaryTradeId);
        } else {
            // 临时不够，还要扣永久，冻结账户增加，临时账户减少到0，永久账户继续减少
            Integer temporaryTradeId = tradeService.trade(freezeAccountId, temporaryAccountId, temporaryCoinAmount, TradeType.APPLY_DO_FRIEND, description);
            Integer foreverTradeId = tradeService.trade(freezeAccountId, foreverAccountId, coinMount - temporaryCoinAmount, TradeType.APPLY_DO_FRIEND, description);
            tradeIds = newHashSet(temporaryTradeId, foreverTradeId);
        }
        return tradeIds;
    }

    @Resource
    private TradeService tradeService;

}
