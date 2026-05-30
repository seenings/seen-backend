package io.github.seenings.apply.controller;

import io.github.seenings.apply.http.HttpUserApplyTradeService;
import io.github.seenings.apply.service.UserApplyTradeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserApplyTradeController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RestController
@AllArgsConstructor
public class UserApplyTradeController implements HttpUserApplyTradeService {
    private UserApplyTradeService userApplyTradeService;

    @Override
    public List<Integer> set( Integer applyId,  List<Long> tradeIds) {
        return userApplyTradeService.set(applyId, tradeIds);
    }
}
