package io.github.seenings.address.http;

import io.github.seenings.common.model.CascaderString;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpProvinceService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_OTHER,
        path = FEIGN_VERSION + "address/province",
        contextId = "HttpProvinceService")
public interface HttpProvinceService {
    @PostMapping("province-code-to-province-id")
    Map<String, Integer> provinceCodeToProvinceId(@RequestBody Set<String> provinceCodes);

    @PostMapping("id-to-name")
    Map<Integer, String> idToName(@RequestBody Set<Integer> ids);

    @PostMapping("list-all")
    List<Map.Entry<String, String>> listAll();

    @GetMapping("to-province-and-city")
    List<CascaderString> toProvinceAndCity();
}
