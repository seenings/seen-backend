package io.github.seenings.coin.api.impl;

import io.github.seenings.coin.api.CoinTradeApi;
import io.github.seenings.coin.repository.CoinBalanceRepository;
import io.github.seenings.coin.repository.CoinBookRepository;
import io.github.seenings.coin.repository.TradeAndBusiRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 玫瑰币交易
 */
@RestController
@AllArgsConstructor
public class CoinTradeApiImpl implements CoinTradeApi {
    /**
     * 玫瑰币余额
     */
    private CoinBalanceRepository coinBalanceRepository;
    /**
     * 玫瑰币记账
     */
    private CoinBookRepository coinBookRepository;
    /**
     * 交易与业务关系
     */
    private TradeAndBusiRepository tradeAndBusiRepository;

    /**
     * 一笔交易
     * @param debitId   借方
     * @param creditId  贷方
     * @param amount    数量
     * @param busiId    业务ID
     * @return  交易时间
     */
    @Override
    public LocalDateTime tradeToTradeTime(Long debitId, Long creditId, Long amount, Long busiId) {
        LocalDateTime tradeTime = LocalDateTime.now();
        //事务
        Long tradeId = coinBookRepository.add(amount, debitId, creditId, tradeTime);
        coinBalanceRepository.update(amount, tradeTime, debitId);
        coinBalanceRepository.update(-amount, tradeTime, creditId);
        tradeAndBusiRepository.add(tradeTime, busiId, tradeId);
        return tradeTime;
    }
}
