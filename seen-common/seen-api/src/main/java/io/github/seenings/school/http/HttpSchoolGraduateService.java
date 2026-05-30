package io.github.seenings.school.http;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * SchoolGraduateService
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@HttpExchange(
        value = SeenConstant.FEIGN_VERSION + "school/graduate")
public interface HttpSchoolGraduateService {
    @PostExchange("user-id-to-graduated")
    Map<Long, Integer> userIdToGraduated(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("graduated") Integer graduated);
}
