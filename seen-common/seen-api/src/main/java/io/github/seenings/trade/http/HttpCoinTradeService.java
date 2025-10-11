package io.github.seenings.trade.http;

import io.github.seenings.coin.enumeration.BusiType;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

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
    Long freezeToSysUse(
            @RequestParam("userId") Long userId,
            @RequestParam("coinMount") Long coinMount,
            @RequestParam("busiType") BusiType busiType,
            @RequestParam("description") String description);

    @PostMapping("freeze-to-temporary")
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
    @PostMapping("check-enough-and-freeze")
    Set<Long> checkEnoughAndFreeze(
            @RequestParam("userId") Long userId,
            @RequestParam("coinMount") Long coinMount,
            @RequestParam("busiType") BusiType busiType,
            @RequestParam("description") String description);

    @PostMapping("check-enough")
    Boolean checkEnough(@RequestParam("userId") Long userId, @RequestParam("coinMount") Long coinMount);

    @PostMapping("simple-trade-type-to")
    Long simpleTradeTypeTo(@RequestParam("userId") Long userId, @RequestParam("coinAmount") Long coinAmount
            , @RequestParam("busiType") BusiType busiType
    );
}
