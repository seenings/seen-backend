package io.github.seenings.work.http;

import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * HttpUserWorkService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@HttpExchange(
        value = SeenConstant.FEIGN_VERSION + "user/work")
public interface HttpUserWorkService {
    @PostExchange("user-id-to-company-name")
    Map<Long, String> userIdToCompanyName(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("companyName") String companyName);
}
