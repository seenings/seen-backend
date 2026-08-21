package io.github.seenings.address.controller;

import io.github.seenings.address.http.HttpProvinceService;
import io.github.seenings.address.service.CityService;
import io.github.seenings.address.service.ProvinceService;
import io.github.seenings.common.model.CascaderString;
import io.github.seenings.core.util.CollUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 省份
 */
@RestController
@AllArgsConstructor
public class ProvinceController implements HttpProvinceService {

    private ProvinceService provinceService;

    private CityService cityService;

    @Override
    public Map<String, Integer> provinceCodeToProvinceId(Set<String> provinceCodes) {
        return provinceService.provinceCodeToProvinceId(provinceCodes);
    }

    @Override
    public Map<Integer, String> idToName(Set<Integer> ids) {
        return provinceService.idToName(ids);
    }

    @Override
    public List<Map.Entry<String, String>> listAll() {
        return provinceService.listAll();
    }


    @Override
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


    @Override
    public Map<String, Set<String>> provinceCodeToCityName(Set<String> provinceCodes) {
        Map<String, List<String>> provinceCodeToCodeMap = cityService.provinceCodeToCode(provinceCodes);
        Set<String> codes = provinceCodeToCodeMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Map<String, String> codeToNameMap = cityService.codeToName(codes);

        return provinceCodes.stream()
                .map(provinceCode -> {
                    List<String> cityCodes = provinceCodeToCodeMap.get(provinceCode);
                    if (CollUtil.isEmpty(cityCodes)) {
                        return null;
                    }
                    Set<String> cityNames = cityCodes.stream()
                            .map(codeToNameMap::get).collect(Collectors.toSet());
                    return Map.entry(provinceCode, cityNames);
                }).filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /// 根据所在地获取省会代码
    ///
    /// @param locations 所在地
    /// @return 所在地对应省会代码
    @Override
    public Map<String, String> locationToProvinceCode(Set<String> locations) {
        Set<String> cityNames = cityService.toCityName(locations);
        Set<String> provinceNames = provinceService.toProvinceName(locations);

        Map<String, String> cityNameToProvinceCode = cityService.cityNameToProvinceCode(cityNames);
        Map<String, String> provinceNameToProvinceCode = provinceService.provinceNameToProvinceCode(provinceNames);

        return locations.stream()
                .map(location -> {
                    if (cn.hutool.core.collection.CollUtil.contains(cityNames, location)) {
                        String provinceCode = cityNameToProvinceCode.get(location);
                        return Map.entry(provinceCode, location);
                    } else if (cn.hutool.core.collection.CollUtil.contains(provinceNames, location)) {
                        return Map.entry(provinceNameToProvinceCode.get(location), location);
                    } else {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}
