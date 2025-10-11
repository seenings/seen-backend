package io.github.seenings.apply.controller;

import io.github.seenings.apply.http.HttpUserApplyTradeService;
import io.github.seenings.apply.service.UserApplyTradeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserApplyTradeController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RestController
@RequestMapping(FEIGN_VERSION + "chat/apply-trade")
public class UserApplyTradeController implements HttpUserApplyTradeService {
    @Resource
    private UserApplyTradeService userApplyTradeService;

    @Override
    @PostMapping("set")
    public List<Integer> set(@RequestParam("applyId") Integer applyId, @RequestBody List<Long> tradeIds) {
        return userApplyTradeService.set(applyId, tradeIds);
    }
}
