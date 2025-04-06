package io.github.seenings.coin.api.impl;

import io.github.seenings.account.http.HttpCoinAccountService;
import io.github.seenings.account.service.CoinAccountBalanceService;
import io.github.seenings.account.service.CoinAccountService;
import io.github.seenings.account.service.CoinAccountUserService;
import io.github.seenings.coin.enumeration.AccountType;
import io.github.seenings.sys.constant.SeenConstant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * CoinAccountController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RequestMapping(SeenConstant.FEIGN_VERSION + "account/coin-account")
@RestController
public class CoinAccountController implements HttpCoinAccountService {

    @Resource
    private CoinAccountService coinAccountService;
    @Resource
    private CoinAccountUserService coinAccountUserService;
    @Resource
    private CoinAccountBalanceService coinAccountBalanceService;

    @Override
    @PostMapping("init-account")
    public void initAccount(@RequestParam("userId") Long userId) {
        Long userForeverAccountId = coinAccountService.createAccount(
                AccountType.USER_FOREVER);
        Long userTemporaryAccountId = coinAccountService.createAccount(
                AccountType.USER_TEMPORARY);
        Long userFreezeAccountId = coinAccountService.createAccount(
                AccountType.USER_FREEZE);

        coinAccountUserService.set(userForeverAccountId, userId);
        coinAccountUserService.set(userTemporaryAccountId, userId);
        coinAccountUserService.set(userFreezeAccountId, userId);

        coinAccountBalanceService.set(userForeverAccountId, 0);
        coinAccountBalanceService.set(userTemporaryAccountId, 0);
        coinAccountBalanceService.set(userFreezeAccountId, 0);
    }

}
