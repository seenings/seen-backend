package io.github.seenings.coin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.github.seenings.account.http.HttpCoinAccountService;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.trade.http.HttpCoinTradeService;

import jakarta.annotation.Resource;

/**
 * CoinController
 *
 * @author chixuehui
 * @since 2023-01-08
 */
@RestController
@RequestMapping(PublicConstant.REST + "coin/coin")
public class CoinController {

    @Resource
    private HttpCoinTradeService httpCoinTradeService;

    @PostMapping("check-enough")
    public R<Boolean> checkEnough(@SessionAttribute Long userId) {
        Boolean enough = httpCoinTradeService.checkEnough(userId, 100);
        return ResUtils.ok(enough);
    }

    @Resource
    private HttpCoinAccountService httpCoinAccountService;

    @PostMapping("init-account")
    public R<String> initAccount(@SessionAttribute Long userId) {

        httpCoinAccountService.initAccount(userId);
        return ResUtils.ok("初始化账户成功");
    }
}
