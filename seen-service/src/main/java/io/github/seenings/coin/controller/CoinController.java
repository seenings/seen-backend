package io.github.seenings.coin.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.trade.http.HttpCoinTradeService;

/**
 * CoinController
 *
 * @author chixuehui
 * @since 2023-01-08
 */
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.REST + "coin/coin")
public class CoinController {

    private HttpCoinTradeService httpCoinTradeService;


    @PostMapping("check-enough")
    public R<Boolean> checkEnough(@SessionAttribute Long userId) {

        Boolean enough = httpCoinTradeService.checkEnough(userId, 100L);
        //TODO
        return ResUtils.ok(enough);
    }
}
