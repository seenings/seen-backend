package io.github.seenings.coin.repository.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import io.github.seenings.coin.po.CoinBook;
import io.github.seenings.coin.repository.CoinBookRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 玫瑰币记账
 */
@Mapper
interface CoinBookMapper extends BaseMapper<CoinBook>{}
/**
 * 玫瑰币记账
 */
@RestController
public class CoinBookRepositoryImpl extends CrudRepository<CoinBookMapper, CoinBook> implements CoinBookRepository {
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
        save(entity);
        return entity.getTradeId();
    }
}
