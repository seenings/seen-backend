package io.github.seenings.coin.repository.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import io.github.seenings.coin.po.CoinBalance;
import io.github.seenings.coin.repository.CoinBalanceRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 玫瑰币余额
 */
@Mapper
interface CoinBalanceMapper extends BaseMapper<CoinBalance> {
}

/**
 * 玫瑰币余额
 */
@Repository
public class CoinBalanceRepositoryImpl extends CrudRepository<CoinBalanceMapper, CoinBalance> implements CoinBalanceRepository {

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
        CoinBalance old = getOne(queryWrapper);
        if (old == null) {
            CoinBalance coinBalance = new CoinBalance().setBalance(amount).setTransactionTime(transactionTime).setDebitOrCreditId(debitOrCreditId);
            return save(coinBalance);
        } else {
            long newBalance = old.getBalance() + amount;
            CoinBalance coinBalance = new CoinBalance().setBalance(newBalance).setTransactionTime(transactionTime).setDebitOrCreditId(debitOrCreditId);
            return update(coinBalance, queryWrapper);
        }
    }
}
