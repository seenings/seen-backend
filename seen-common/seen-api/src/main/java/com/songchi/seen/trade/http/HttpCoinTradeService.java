package com.songchi.seen.trade.http;

import com.songchi.seen.coin.enumeration.TradeType;
import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpCoinTradeService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_COIN,
        path = FEIGN_VERSION + "coin/trade",
        contextId = "HttpCoinTradeService")
public interface HttpCoinTradeService {
    @PostMapping("freeze-to-sys-use")
    Integer freezeToSysUse(
            @RequestParam("userId") Integer userId,
            @RequestParam("coinMount") int coinMount,
            @RequestParam("tradeType") TradeType tradeType,
            @RequestParam("description") String description);

    @PostMapping("freeze-to-temporary")
    Integer freezeToTemporary(
            @RequestParam("userId") Integer userId,
            @RequestParam("coinMount") int coinMount,
            @RequestParam("tradeType") TradeType tradeType,
            @RequestParam("description") String description);

    /**
     * 检查资金是否足够，如果够冻结
     * @param userId    用户ID
     * @param coinMount 虚拟币个数
     * @return  冻结的交易ID，资金不够时返回空
     */
    @PostMapping("check-enough-and-freeze")
    Set<Integer> checkEnoughAndFreeze(
            @RequestParam("userId") Integer userId,
            @RequestParam("coinMount") int coinMount,
            @RequestParam("tradeType") TradeType tradeType,
            @RequestParam("description") String description);

    @PostMapping("check-enough")
    Boolean checkEnough(@RequestParam("userId") Integer userId, @RequestParam("coinMount") int coinMount);

    @PostMapping("simple-trade-type-to")
    Integer simpleTradeTypeTo(@RequestParam("userId") Integer userId, @RequestParam("coinAmount") Integer coinAmount
            , @RequestParam("tradeType") TradeType tradeType
    );
}
