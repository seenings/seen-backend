package com.songchi.seen.school.http;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;

/**
 * HttpStudentInfoService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_SCHOOL,
        path = SeenConstant.FEIGN_VERSION + "school/student-info",
        contextId = "HttpStudentInfoService")
public interface HttpStudentInfoService {
    @PostMapping("user-id-school-id")
    Map<Long, Integer> userIdToSchoolId(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("schoolId") Integer schoolId);
}
