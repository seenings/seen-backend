package com.songchi.seen.school.http;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * SchoolGraduateService
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_SCHOOL,
        path = SeenConstant.FEIGN_VERSION + "school/graduate",
        contextId = "HttpSchoolGraduateService")
public interface HttpSchoolGraduateService {
    @PostMapping("user-id-to-graduated")
    Map<Long, Integer> userIdToGraduated(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("graduated") Integer graduated);
}
