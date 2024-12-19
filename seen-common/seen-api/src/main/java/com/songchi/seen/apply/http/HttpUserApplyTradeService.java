package com.songchi.seen.apply.http;

import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserApplyTradeService
 *
 * @author chixuehui
 * @since 2023-03-05
 */

@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_CHAT,
        path = FEIGN_VERSION + "chat/apply-trade",
        contextId = "HttpUserApplyTradeService")
public interface HttpUserApplyTradeService {
    @PostMapping("set")
    List<Integer> set(@RequestParam("applyId") Integer applyId, @RequestBody List<Integer> tradeIds);
}
