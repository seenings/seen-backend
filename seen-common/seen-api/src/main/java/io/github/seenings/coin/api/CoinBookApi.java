package io.github.seenings.coin.api;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.time.LocalDateTime;

/**
 * 玫瑰币记账
 */
@HttpExchange(SeenConstant.SEEN_SMALL + "/coin/coin/coin-book")
public interface CoinBookApi {

    /**
     * 增加
     *
     * @param amount          数量
     * @param debitId         借方
     * @param creditId        贷方
     * @param transactionTime 成交时间
     * @return 交易ID
     */
    @GetExchange("add")
    Long add(Long amount, Long debitId, Long creditId, LocalDateTime transactionTime);

}
