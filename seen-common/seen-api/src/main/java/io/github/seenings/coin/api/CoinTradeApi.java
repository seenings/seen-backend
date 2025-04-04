package io.github.seenings.coin.api;

import com.songchi.seen.sys.constant.SeenConstant;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.time.LocalDateTime;

/**
 * 玫瑰币交易
 */
@HttpExchange(SeenConstant.SEEN_SMALL + "/coin/coin/coin-trade")
public interface CoinTradeApi {
    /**
     * 做一笔交易,玫瑰币变化
     *
     * @param debitId  借方
     * @param creditId 贷方
     * @param amount   数量
     * @param busiId   业务ID
     * @return 成交时间
     */
    @GetExchange("trade-to-trade-time")
    LocalDateTime tradeToTradeTime(Long debitId, Long creditId, Long amount, Long busiId);

}
