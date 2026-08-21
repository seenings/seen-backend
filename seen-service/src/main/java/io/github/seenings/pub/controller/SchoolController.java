package io.github.seenings.pub.controller;

import cn.hutool.core.collection.CollUtil;
import io.github.seenings.address.http.HttpProvinceService;
import io.github.seenings.common.model.CascaderString;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.core.util.NumberUtils;
import io.github.seenings.school.http.HttpSchoolService;
import io.github.seenings.sys.constant.PublicConstant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 学校信息 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2021-05-23
 */
@AllArgsConstructor
@RestController
@RequestMapping(PublicConstant.PUBLIC + "pub/school")
public class SchoolController {

    private HttpProvinceService httpProvinceService;

    private HttpSchoolService httpSchoolService;

    @GetMapping("to-province-and-school")
    public R<List<CascaderString>> toProvinceAndSchool() {
        List<Map.Entry<String, String>> entries = httpProvinceService.listAll();

        // 根据省份的code，拿到省份的名称，再拿到学校的标识码
        // 根据省份的code，拿到城市的code
        // 根据城市的code，拿到城市的名称，拿到学校的标识码
        //做汇总
        Set<String> provinceCodes = entries.stream().map(Map.Entry::getKey).collect(Collectors.toSet());
        Map<String, Integer> provinceCodeToProvinceIdMap = httpProvinceService.provinceCodeToProvinceId(provinceCodes);
        Set<Integer> provinceIds = new HashSet<>(provinceCodeToProvinceIdMap.values());
        Map<Integer, String> provinceIdsToName = httpProvinceService.idToName(provinceIds);

        Map<String, Set<String>> provinceNameToSchoolCode = httpSchoolService.locationToSchoolCode(new HashSet<>(provinceIdsToName.values()));

        Map<String, Set<String>> provinceCodeToCityName = httpProvinceService.provinceCodeToCityName(provinceCodes);

        Map<String, Set<String>> cityNameToSchoolCode = httpSchoolService.locationToSchoolCode(provinceCodeToCityName.values()
                .stream().flatMap(Collection::stream).collect(Collectors.toSet()));
        Map<String, Set<String>> provinceCodeToSchoolCode = provinceCodes.stream()
                .map(provinceCode -> {
                    Integer provinceId = provinceCodeToProvinceIdMap.get(provinceCode);
                    String provinceName = provinceIdsToName.get(provinceId);
                    Set<String> schoolCodeByProvince = provinceNameToSchoolCode.get(provinceName);
                    Set<String> cityNames = provinceCodeToCityName.get(provinceCode);
                    Set<String> schoolCOdeByCity = cityNameToSchoolCode.entrySet()
                            .stream().filter(n -> cityNames.contains(n.getKey()))
                            .map(Map.Entry::getValue)
                            .flatMap(Collection::stream).collect(Collectors.toSet());
                    return Map.entry(provinceCode, new HashSet<>(CollUtil.union(schoolCodeByProvince, schoolCOdeByCity)));

                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Set<String> schoolCodes = provinceCodeToSchoolCode.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Map<String, String> schoolCodeToSchoolName = httpSchoolService.idToSchoolName(schoolCodes);
        List<CascaderString> result = entries.stream()
                .map(n -> {
                    String provinceCode = n.getKey();
                    String provinceName = n.getValue();
                    Set<String> resultSchoolIds = provinceCodeToSchoolCode.get(provinceCode);
                    List<CascaderString> c;
                    if (resultSchoolIds == null) {
                        c = null;
                    } else {
                        c = resultSchoolIds.stream()
                                .map(schoolId -> {
                                    String schoolName = schoolCodeToSchoolName.get(schoolId);
                                    return new CascaderString(schoolId, schoolName, null);
                                })
                                .collect(Collectors.toList());
                    }
                    Integer provinceId = provinceCodeToProvinceIdMap.get(provinceCode);
                    return new CascaderString(NumberUtils.intToString(provinceId), provinceName, c);
                })
                .collect(Collectors.toList());
        return ResUtils.ok(result);
    }
}
