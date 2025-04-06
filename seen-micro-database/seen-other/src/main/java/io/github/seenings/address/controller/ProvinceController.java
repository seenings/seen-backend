package io.github.seenings.address.controller;

import io.github.seenings.address.http.HttpProvinceService;
import io.github.seenings.address.service.CityService;
import io.github.seenings.address.service.ProvinceService;
import io.github.seenings.common.model.CascaderString;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * ProvinceController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "address/province")
public class ProvinceController implements HttpProvinceService {

    @Resource
    private ProvinceService provinceService;

    @Override
    @PostMapping("province-code-to-province-id")
    public Map<String, Integer> provinceCodeToProvinceId(@RequestBody Set<String> provinceCodes) {
        return provinceService.provinceCodeToProvinceId(provinceCodes);
    }

    @Override
    @PostMapping("id-to-name")
    public Map<Integer, String> idToName(@RequestBody Set<Integer> ids) {
        return provinceService.idToName(ids);
    }

    @Override
    @PostMapping("list-all")
    public List<Map.Entry<String, String>> listAll() {
        return provinceService.listAll();
    }

    @Resource
    private CityService cityService;

    @Override
    @GetMapping("to-province-and-city")
    public List<CascaderString> toProvinceAndCity() {
        List<Map.Entry<String, String>> entries = provinceService.listAll();
        Set<String> provinceCodes =
                entries.stream().parallel().map(Map.Entry::getKey).collect(Collectors.toSet());
        Map<String, Integer> provinceCodeToProvinceIdMap = provinceService.provinceCodeToProvinceId(provinceCodes);
        Map<String, List<String>> provinceCodeToCodeMap = cityService.provinceCodeToCode(provinceCodes);
        Set<String> codes = provinceCodeToCodeMap.values().stream()
                .parallel()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Map<String, Integer> cityCodeToCityIdMap = cityService.cityCodeToCityId(codes);
        Map<String, String> codeToNameMap = cityService.codeToName(codes);
        return entries.stream()
                .map(n -> {
                    String provinceCode = n.getKey();
                    String provinceName = n.getValue();
                    List<String> cityCodes = provinceCodeToCodeMap.get(provinceCode);
                    List<CascaderString> c;
                    if (cityCodes == null) {
                        c = null;
                    } else {
                        c = cityCodes.stream()
                                .map(cityCode -> {
                                    String cityName = codeToNameMap.get(cityCode);
                                    Integer cityId = cityCodeToCityIdMap.get(cityCode);
                                    return new CascaderString(cityId + "", cityName, null);
                                })
                                .collect(Collectors.toList());
                    }
                    Integer provinceId = provinceCodeToProvinceIdMap.get(provinceCode);
                    return new CascaderString(provinceId + "", provinceName, c);
                })
                .collect(Collectors.toList());
    }
}
