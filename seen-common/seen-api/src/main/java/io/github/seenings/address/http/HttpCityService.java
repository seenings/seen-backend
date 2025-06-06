package io.github.seenings.address.http;

import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpCityService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_OTHER,
        path = FEIGN_VERSION + "address/city",
        contextId = "HttpCityService")
public interface HttpCityService {
    @PostMapping("id-to-name")
    Map<Integer, String> idToName(@RequestBody Set<Integer> ids);
}
