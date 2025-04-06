package io.github.seenings.coin.api.impl;

import io.github.seenings.account.http.HttpCoinAccountService;
import io.github.seenings.account.service.CoinAccountService;
import io.github.seenings.account.service.CoinAccountUserService;
import io.github.seenings.account.service.FreezeService;
import io.github.seenings.coin.enumeration.AccountType;
import io.github.seenings.coin.enumeration.TradeType;
import io.github.seenings.trade.http.HttpCoinTradeService;
import io.github.seenings.trade.service.TradeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * CoinTradeController
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@RestController
@RequestMapping(FEIGN_VERSION + "coin/trade")
public class CoinTradeController implements HttpCoinTradeService {

    @Resource
    private FreezeService freezeService;

    @Override
    @PostMapping("freeze-to-sys-use")
    public Integer freezeToSysUse(@RequestParam("userId") Long userId, @RequestParam("coinMount") int coinMount, @RequestParam("tradeType") TradeType tradeType, @RequestParam("description") String description) {

        return freezeService.freezeToSysUse(userId, coinMount, tradeType, description);

    }

    @Override
    @PostMapping("freeze-to-temporary")
    public Integer freezeToTemporary(@RequestParam("userId") Long userId, @RequestParam("coinMount") int coinMount, @RequestParam("tradeType") TradeType tradeType, @RequestParam("description") String description) {
        return freezeService.freezeToTemporary(userId, coinMount, tradeType, description);
    }

    /**
     * 检查资金是否足够，如果够冻结
     *
     * @param userId    用户ID
     * @param coinMount 虚拟币个数
     * @return 冻结的交易ID，资金不够时返回空
     */
    @Override
    @PostMapping("check-enough-and-freeze")
    public Set<Integer> checkEnoughAndFreeze(@RequestParam("userId") Long userId, @RequestParam("coinMount") int coinMount, @RequestParam("tradeType") TradeType tradeType, @RequestParam("description") String description) {

        return freezeService.checkEnoughAndFreeze(userId, coinMount, tradeType, description);
    }

    /**
     * 检查是否有足够的虚拟币
     *
     * @param userId    用户ID
     * @param coinMount 币的数量
     * @return 是否足够
     */
    @Override
    @PostMapping("check-enough")
    public Boolean checkEnough(@RequestParam("userId") Long userId, @RequestParam("coinMount") int coinMount) {

        return freezeService.checkEnough(userId, coinMount);
    }

    @Resource
    private CoinAccountService coinAccountService;

    @Resource
    private TradeService tradeService;
    @Resource
    private CoinAccountUserService coinAccountUserService;

    @Override
    @PostMapping("simple-trade-type-to")
    public Integer simpleTradeTypeTo(@RequestParam("userId") Long userId, @RequestParam("coinAmount") Integer coinAmount, @RequestParam("tradeType") TradeType tradeType) {
        Long temporaryAccountId = coinAccountUserService.userIdToAccountId(Set.of(userId)).get(userId).stream().findFirst().orElse(null);
        int sysRewardAccountTypeId = AccountType.SYS_REWARD.getIndex();
        List<Long> accountIds = coinAccountService.accountTypeToAccountId(Collections.singleton(sysRewardAccountTypeId)).get(sysRewardAccountTypeId);
        Long sysRewardAccountId = Optional.ofNullable(accountIds).orElse(Collections.emptyList()).stream().findFirst().orElse(null);

        return tradeService.sysOutTrade(temporaryAccountId, sysRewardAccountId, coinAmount, tradeType, tradeType.getLabel());
    }
}
