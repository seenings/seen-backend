package com.songchi.seen.apply.http;

import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserApplyRefuseService
 *
 * @author chixuehui
 * @since 2023-03-05
 */

@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_CHAT,
        path = FEIGN_VERSION + "chat/apply-refuse",
        contextId = "HttpUserApplyRefuseService")
public interface HttpUserApplyRefuseService {
    @PostMapping("apply-id-to-refuse-time")
    Map<Integer, LocalDateTime> applyIdToRefuseTime(@RequestBody Set<Integer> applyIds );

    @PostMapping("add")
    Integer  add(@RequestParam("applyId") Integer applyId, @RequestParam("textId") Integer textId);
}
