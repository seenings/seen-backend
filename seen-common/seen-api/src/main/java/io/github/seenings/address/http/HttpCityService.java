package io.github.seenings.address.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * 城市
 */
@HttpExchange(FEIGN_VERSION + "address/city")
public interface HttpCityService {
    @PostExchange("id-to-name")
    Map<Integer, String> idToName(@RequestBody Set<Integer> ids);
}
