package io.github.seenings.school.http;

import io.github.seenings.school.enumeration.Education;
import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * EducationalHttpService
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_SCHOOL,
        path = SeenConstant.FEIGN_VERSION + "school/educational",
        contextId = "HttpEducationalService")
public interface HttpEducationalService {

    @PostMapping("user-id-to-educational")
    Map<Long, Integer> userIdToEducational(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("education") Education education);
}
