package io.github.seenings.coin.controller;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * 玫瑰币余额
 */
@HttpExchange(SeenConstant.SEEN_SMALL + "/coin/coin/coin-balance")
public interface CoinBalanceController {

    /**
     * 根据账户ID获取余额
     *
     * @param debitOrCreditIds 账户ID
     * @return 账户ID对应余额
     */
    @PostExchange("debit-or-credit-id-to-balance")
    Map<Long, Long> debitOrCreditIdToBalance(@RequestBody Set<Long> debitOrCreditIds);
}
