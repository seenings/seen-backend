package io.github.seenings.school.http;

import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * HttpStudentInfoService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@HttpExchange(
        value = SeenConstant.FEIGN_VERSION + "school/student-info")
public interface HttpStudentInfoService {
    @PostExchange("user-id-school-id")
    Map<Long, Integer> userIdToSchoolId(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("schoolId") Integer schoolId);
}
