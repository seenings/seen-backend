package io.github.seenings.coin.service.impl;

import io.github.seenings.account.service.CoinAccountBalanceService;
import io.github.seenings.account.service.CoinAccountService;
import io.github.seenings.account.service.CoinAccountUserService;
import io.github.seenings.account.service.FreezeService;
import io.github.seenings.coin.controller.CoinBalanceController;
import io.github.seenings.coin.enumeration.AccountType;
import io.github.seenings.coin.enumeration.BusiType;
import io.github.seenings.trade.service.TradeService;
import io.github.seenings.coin.api.CoinAccountApi;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class FreezeServiceImpl implements FreezeService {

    @Resource
    private CoinAccountUserService coinAccountUserService;

    @Resource
    private CoinAccountService coinAccountService;

    @Resource
    private CoinAccountBalanceService coinAccountBalanceService;

    /**
     * 玫瑰币账户设置
     */
    private CoinAccountApi coinAccountApi;

    /**
     * 冻结账户转给系统使用账户
     *
     * @param userId      用户ID
     * @param coinMount   虚拟币数量
     * @param busiType    交易类型
     * @param description 描述
     * @return 交易ID
     */
    @Override
    public Long freezeToSysUse(Long userId, Long coinMount, BusiType busiType, String description) {

        Long freezeAccountId = coinAccountApi.userIdToAccountId(Collections.singleton(userId)).get(userId);
        int sysUseAccountTypeId = AccountType.SYS_USE.getIndex();
        List<Long> accountIds = coinAccountService.accountTypeToAccountId(Collections.singleton(sysUseAccountTypeId)).get(sysUseAccountTypeId);
        Long sysUseAccountId = accountIds.stream().findFirst().orElse(null);

        return tradeService.sysInTrade(sysUseAccountId, freezeAccountId, coinMount, busiType, description);
    }

    @Override
    public Long freezeToTemporary(Long userId, Long coinMount, BusiType busiType, String description) {

        Long freezeAccountId = coinAccountApi.userIdToAccountId(Collections.singleton(userId)).get(userId);
        Long temporaryAccountId = coinAccountApi.userIdToAccountId(Collections.singleton(userId)).get(userId);

        return tradeService.trade(temporaryAccountId, freezeAccountId, coinMount, busiType, description);
    }

    private CoinBalanceController coinBalanceController;

    /**
     * 检查是否有足够的虚拟币
     *
     * @param userId    用户ID
     * @param coinMount 币的数量
     * @return 是否足够
     */
    @Override
    public Boolean checkEnough(Long userId, Long coinMount) {

        // 1.检查用户可用账户是否有足够的虚拟币
        Long userAccountId = coinAccountApi.userIdToAccountId(Set.of(userId)).get(userId);
        Long balance = coinBalanceController.debitOrCreditIdToBalance(Set.of(userAccountId)).get(userAccountId);
        return balance >= coinMount;
    }

    /**
     * 检查资金是否足够，如果够冻结
     *
     * @param userId    用户ID
     * @param coinMount 虚拟币个数
     * @return 冻结的交易ID，资金不够时返回空
     */
    @Override
    public Set<Long> checkEnoughAndFreeze(Long userId, Long coinMount, BusiType busiType, String description) {

        // 对用户账户进行锁定 //TODO，使用redis锁
        // 1.检查用户可用账户是否有足够的虚拟币
        Set<Long> accountIds = coinAccountUserService.userIdToAccountId(Collections.singleton(userId)).get(userId);
        Map<Long, AccountType> accountIdToAccountTypeMap = coinAccountService.accountIdToAccountType(accountIds);
        // 获取可用账户
        // 永久
        Long foreverAccountId = accountIdToAccountTypeMap.entrySet().stream().filter(n -> n.getValue() == AccountType.USER_FOREVER).map(Map.Entry::getKey).findFirst().orElse(null);
        // 临时
        Long temporaryAccountId = accountIdToAccountTypeMap.entrySet().stream().filter(n -> n.getValue() == AccountType.USER_TEMPORARY).map(Map.Entry::getKey).findFirst().orElse(null);
        // 冻结
        Long freezeAccountId = accountIdToAccountTypeMap.entrySet().stream().filter(n -> n.getValue() == AccountType.USER_FREEZE).map(Map.Entry::getKey).findFirst().orElse(null);
        // 获取账户的余额，如何保证一次访问
        Map<Long, Integer> accountIdCoinAmountMap = coinAccountBalanceService.accountIdCoinAmount(accountIdToAccountTypeMap.keySet());
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
        Set<Long> tradeIds;
        if (temporaryCoinAmount >= coinMount) {
            // 临时够，冻结账户增加，临时账户减少

            Long temporaryTradeId = tradeService.trade(freezeAccountId, temporaryAccountId, coinMount, BusiType.APPLY_DO_FRIEND, description);
            tradeIds = newHashSet(temporaryTradeId);
        } else {
            // 临时不够，还要扣永久，冻结账户增加，临时账户减少到0，永久账户继续减少
            Long temporaryTradeId = tradeService.trade(freezeAccountId, temporaryAccountId, Long.valueOf(temporaryCoinAmount), BusiType.APPLY_DO_FRIEND, description);
            Long foreverTradeId = tradeService.trade(freezeAccountId, foreverAccountId, coinMount - temporaryCoinAmount, BusiType.APPLY_DO_FRIEND, description);
            tradeIds = newHashSet(temporaryTradeId, foreverTradeId);
        }
        return tradeIds;
    }

    @Resource
    private TradeService tradeService;

}
