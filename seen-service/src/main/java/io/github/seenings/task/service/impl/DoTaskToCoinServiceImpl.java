package io.github.seenings.task.service.impl;

import com.songchi.seen.coin.enumeration.AccountType;
import io.github.seenings.coin.api.CoinAccountApi;
import io.github.seenings.coin.api.CoinTradeApi;
import io.github.seenings.coin.enumeration.CommissionRate;
import io.github.seenings.coin.enumeration.DividendRate;
import io.github.seenings.task.service.DoTaskToCoinService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.Set;

/**
 * 做任务产生币
 */
@Service
@AllArgsConstructor
public class DoTaskToCoinServiceImpl implements DoTaskToCoinService {
    /**
     * 玫瑰币账户设置
     */
    private CoinAccountApi coinAccountApi;
    /**
     * 玫瑰币交易
     */
    private CoinTradeApi coinTradeApi;

    /**
     * 分配币
     *
     * @param userId     用户
     * @param busiId     业务ID
     * @param coinAmount 币的数量
     */
    @Override
    public void doTask(Long userId, Long busiId, Long coinAmount) {

        //做任务 借方为用户,贷方为玫瑰币基础账户,数量为任务玫瑰币数量
        //做任务佣金 借方为系统佣金账户,贷方为用户,数量为任务玫瑰币数量佣金比例
        //做任务分红 借方为系统分红账户,贷方为用户,数量为任务玫瑰币数量分红比例
        //
        //做任务分红会定期根据分红策略分给所有用户
        Long userAccountId = coinAccountApi.userIdToAccountId(Set.of(userId)).get(userId);
        Map<AccountType, Long> accountTypeToAccountIdMap = coinAccountApi.sysAccountTypeToAccountId(Set.of(AccountType.COIN_BASIC, AccountType.SYS_COMMISSION, AccountType.SYS_DIVIDEND));
        Long coinBasicAccountId = accountTypeToAccountIdMap.get(AccountType.COIN_BASIC);
        Long sysCommissionAccountId = accountTypeToAccountIdMap.get(AccountType.SYS_COMMISSION);
        Long sysDividendAccountId = accountTypeToAccountIdMap.get(AccountType.SYS_DIVIDEND);

        //佣金
        BigDecimal commission = CommissionRate.DO_TASK.getRate().multiply(BigDecimal.valueOf(coinAmount), MathContext.UNLIMITED);
        //分红
        BigDecimal dividend = DividendRate.DO_TASK.getRate().multiply(BigDecimal.valueOf(coinAmount), MathContext.UNLIMITED);
        coinTradeApi.tradeToTradeTime(userAccountId, coinBasicAccountId, coinAmount, busiId);
        coinTradeApi.tradeToTradeTime(sysCommissionAccountId, userAccountId, commission.longValue(), busiId);
        coinTradeApi.tradeToTradeTime(sysDividendAccountId, userAccountId, dividend.longValue(), busiId);

    }

}
