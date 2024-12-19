package com.songchi.seen.info.http;

import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpWorkPositionService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = FEIGN_VERSION + "user/work-position",
        contextId = "HttpWorkPositionService")
public interface HttpWorkPositionService {
    @PostMapping("position-id-to-position-name")
    Map<Integer, String> positionIdToPositionName(@RequestBody Set<Integer> ids);

    @PostMapping("exists")
    boolean exists(@RequestParam("positionName") String positionName);

    @PostMapping("set")
    boolean set(@RequestParam("positionName") String positionName);

    @GetMapping("work-position")
    Map<Integer, String> workPosition();
}
