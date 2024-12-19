package com.songchi.seen.apply.controller;

import com.songchi.seen.apply.http.HttpUserApplyTradeService;
import com.songchi.seen.apply.service.UserApplyTradeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

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
    public List<Integer> set(@RequestParam("applyId") Integer applyId, @RequestBody List<Integer> tradeIds) {
        return userApplyTradeService.set(applyId, tradeIds);
    }
}
