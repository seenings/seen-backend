package io.github.seenings.trade.http;

import io.github.seenings.coin.enumeration.BusiType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpCoinTradeService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@HttpExchange(
        value = FEIGN_VERSION + "coin/trade")
public interface HttpCoinTradeService {
    @PostExchange("freeze-to-sys-use")
    Long freezeToSysUse(
            @RequestParam("userId") Long userId,
            @RequestParam("coinMount") Long coinMount,
            @RequestParam("busiType") BusiType busiType,
            @RequestParam("description") String description);

    @PostExchange("freeze-to-temporary")
    Long freezeToTemporary(
            @RequestParam("userId") Long userId,
            @RequestParam("coinMount") Long coinMount,
            @RequestParam("busiType") BusiType busiType,
            @RequestParam("description") String description);

    /**
     * 检查资金是否足够，如果够冻结
     * @param userId    用户ID
     * @param coinMount 虚拟币个数
     * @return  冻结的交易ID，资金不够时返回空
     */
    @PostExchange("check-enough-and-freeze")
    Set<Long> checkEnoughAndFreeze(
            @RequestParam("userId") Long userId,
            @RequestParam("coinMount") Long coinMount,
            @RequestParam("busiType") BusiType busiType,
            @RequestParam("description") String description);

    @PostExchange("check-enough")
    Boolean checkEnough(@RequestParam("userId") Long userId, @RequestParam("coinMount") Long coinMount);

    @PostExchange("simple-trade-type-to")
    Long simpleTradeTypeTo(@RequestParam("userId") Long userId, @RequestParam("coinAmount") Long coinAmount
            , @RequestParam("busiType") BusiType busiType
    );
}
