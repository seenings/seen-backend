package com.songchi.seen.coin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.songchi.seen.account.http.HttpCoinAccountService;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.trade.http.HttpCoinTradeService;

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
