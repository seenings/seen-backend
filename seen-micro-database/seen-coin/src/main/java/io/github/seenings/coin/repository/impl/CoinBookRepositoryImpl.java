package io.github.seenings.coin.repository.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.coin.po.CoinBook;
import io.github.seenings.coin.repository.CoinBookRepository;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 玫瑰币记账
 */
@Mapper
interface CoinBookMapper extends BaseMapper<CoinBook>{}
/**
 * 玫瑰币记账
 */
@AllArgsConstructor
@Repository
public class CoinBookRepositoryImpl  implements CoinBookRepository {

    private CoinBookMapper coinBookMapper;
    /**
     * 增加
     *
     * @param amount          数量
     * @param debitId         借方
     * @param creditId        贷方
     * @param transactionTime 成交时间
     * @return 交易ID
     */
    @Override
    public Long add(Long amount, Long debitId, Long creditId, LocalDateTime transactionTime) {
        CoinBook entity = new CoinBook().setAmount(amount).setCreditId(creditId).setDebitId(debitId).setTransactionTime(transactionTime);
        coinBookMapper.insert(entity);
        return entity.getTradeId();
    }
}
