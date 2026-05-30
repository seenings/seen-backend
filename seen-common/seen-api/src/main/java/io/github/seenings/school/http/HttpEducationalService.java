package io.github.seenings.school.http;

import io.github.seenings.school.enumeration.Education;
import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * EducationalHttpService
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@HttpExchange(
        value = SeenConstant.FEIGN_VERSION + "school/educational")
public interface HttpEducationalService {

    @PostExchange("user-id-to-educational")
    Map<Long, Integer> userIdToEducational(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("education") Education education);
}
