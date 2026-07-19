package io.github.seenings.coin.repository.impl;


import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.coin.controller.CoinBalanceController;
import io.github.seenings.coin.po.CoinBalance;
import io.github.seenings.coin.repository.CoinBalanceRepository;
import io.github.seenings.sys.util.ListUtils;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 玫瑰币余额
 */
@Mapper
interface CoinBalanceMapper extends BaseMapper<CoinBalance> {
}

/**
 * 玫瑰币余额
 */
@AllArgsConstructor
@Repository
@RestController
public class CoinBalanceRepositoryImpl  implements CoinBalanceRepository, CoinBalanceController {

    private CoinBalanceMapper coinBalanceMapper;

    /**
     * 更新余额
     *
     * @param amount          数量
     * @param transactionTime 成交时间
     * @param debitOrCreditId 借方或者贷方ID
     * @return 是否更新成功
     */
    @Override
    public boolean update(Long amount, LocalDateTime transactionTime, Long debitOrCreditId) {
        LambdaQueryWrapper<CoinBalance> queryWrapper = new QueryWrapper<CoinBalance>().lambda().eq(CoinBalance::getDebitOrCreditId, debitOrCreditId);
        CoinBalance old = coinBalanceMapper.selectOne(queryWrapper);
        if (old == null) {
            CoinBalance coinBalance = new CoinBalance().setBalance(amount).setTransactionTime(transactionTime).setDebitOrCreditId(debitOrCreditId);
            return coinBalanceMapper.insert(coinBalance)>0;
        } else {
            long newBalance = old.getBalance() + amount;
            CoinBalance coinBalance = new CoinBalance().setBalance(newBalance).setTransactionTime(transactionTime).setDebitOrCreditId(debitOrCreditId);
            return coinBalanceMapper.update(coinBalance, queryWrapper)>0;
        }
    }

    /**
     * 根据账户ID获取余额
     *
     * @param debitOrCreditIds 账户ID
     * @return 账户ID对应余额
     */
    @Override
    public Map<Long, Long> debitOrCreditIdToBalance(Set<Long> debitOrCreditIds) {
        return ListUtil.partition(ListUtils.valueIsNull(debitOrCreditIds), 100)
                .stream().parallel()
                .flatMap(sub -> coinBalanceMapper.selectList(new QueryWrapper<CoinBalance>()
                        .lambda().in(CoinBalance::getDebitOrCreditId, sub).select(CoinBalance::getDebitOrCreditId, CoinBalance::getBalance)).stream())
                .collect(Collectors.toMap(CoinBalance::getDebitOrCreditId, CoinBalance::getBalance));
    }
}
