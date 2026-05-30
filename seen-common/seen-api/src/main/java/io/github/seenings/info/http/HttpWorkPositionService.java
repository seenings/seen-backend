package io.github.seenings.info.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpWorkPositionService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@HttpExchange(
        value = FEIGN_VERSION + "user/work-position")
public interface HttpWorkPositionService {
    @PostExchange("position-id-to-position-name")
    Map<Integer, String> positionIdToPositionName(@RequestBody Set<Integer> ids);

    @PostExchange("exists")
    boolean exists(@RequestParam("positionName") String positionName);

    @PostExchange("set")
    boolean set(@RequestParam("positionName") String positionName);

    @PostExchange("work-position")
    Map<Integer, String> workPosition();
}
