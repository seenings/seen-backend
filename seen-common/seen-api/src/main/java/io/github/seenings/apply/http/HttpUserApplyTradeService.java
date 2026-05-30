package io.github.seenings.apply.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserApplyTradeService
 *
 * @author chixuehui
 * @since 2023-03-05
 */

@HttpExchange(
        value = FEIGN_VERSION + "chat/apply-trade")
public interface HttpUserApplyTradeService {
    @PostExchange("set")
    List<Integer> set(@RequestParam("applyId") Integer applyId, @RequestBody List<Long> tradeIds);
}
