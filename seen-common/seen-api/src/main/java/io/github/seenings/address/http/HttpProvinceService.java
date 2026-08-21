package io.github.seenings.address.http;

import io.github.seenings.common.model.CascaderString;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * 省份
 */
@HttpExchange(FEIGN_VERSION + "address/province")
public interface HttpProvinceService {
    @PostExchange("province-code-to-province-id")
    Map<String, Integer> provinceCodeToProvinceId(@RequestBody Set<String> provinceCodes);

    @PostExchange("id-to-name")
    Map<Integer, String> idToName(@RequestBody Set<Integer> ids);

    @PostExchange("list-all")
    List<Map.Entry<String, String>> listAll();

    @GetExchange("to-province-and-city")
    List<CascaderString> toProvinceAndCity();

    /// 根据省份代码获取城市名称
    ///
    /// @param provinceCodes 省份代码
    /// @return 省份代码对应城市名称
    @PostExchange("province-code-to-city-name")
    Map<String, Set<String>> provinceCodeToCityName(@RequestBody Set<String> provinceCodes);

    /// 根据所在地获取省会代码
    ///
    /// @param locations 所在地
    /// @return 所在地对应省会代码
    @PostExchange("location-to-province-code")
    Map<String, String> locationToProvinceCode(@RequestBody Set<String> locations);
}
