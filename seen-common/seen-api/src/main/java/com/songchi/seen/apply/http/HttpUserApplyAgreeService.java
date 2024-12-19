package com.songchi.seen.apply.http;

import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserApplyAgreeService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_CHAT,
        path = FEIGN_VERSION + "chat/apply-agree",
        contextId = "HttpUserApplyAgreeService")
public interface HttpUserApplyAgreeService {
    @PostMapping("apply-id-to-agree-time")
    Map<Integer, LocalDateTime> applyIdToAgreeTime
            (@RequestBody Set<Integer> applyIds);

    @PostMapping("set")
    Integer set(@RequestParam("applyId") Integer applyId,
                @RequestParam("agreeTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime agreeTime);
}