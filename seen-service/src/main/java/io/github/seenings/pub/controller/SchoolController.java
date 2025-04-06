package io.github.seenings.pub.controller;

import io.github.seenings.address.http.HttpProvinceService;
import io.github.seenings.common.model.CascaderString;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.core.util.NumberUtils;
import io.github.seenings.school.http.HttpSchoolService;
import io.github.seenings.sys.constant.PublicConstant;
import jakarta.annotation.Resource;
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
@RestController
@RequestMapping(PublicConstant.PUBLIC + "pub/school")
public class SchoolController {
    @Resource
    private HttpProvinceService httpProvinceService;

    @Resource
    private HttpSchoolService httpSchoolService;

    @GetMapping("to-province-and-school")
    public R<List<CascaderString>> toProvinceAndSchool() {
        List<Map.Entry<String, String>> entries = httpProvinceService.listAll();
        Set<String> provinceCodes =
                entries.stream().parallel().map(Map.Entry::getKey).collect(Collectors.toSet());
        Map<String, Integer> provinceCodeToProvinceIdMap = httpProvinceService.provinceCodeToProvinceId(provinceCodes);
        Map<String, List<Integer>> provinceCodeToSchoolIdMap = httpSchoolService.provinceCodeToSchoolId(provinceCodes);
        Set<Integer> schoolIds = provinceCodeToSchoolIdMap.values().stream()
                .parallel()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Map<Integer, String> schoolIdToNameMap = httpSchoolService.idToSchoolName(schoolIds);
        List<CascaderString> result = entries.stream()
                .map(n -> {
                    String provinceCode = n.getKey();
                    String provinceName = n.getValue();
                    List<Integer> resultSchoolIds = provinceCodeToSchoolIdMap.get(provinceCode);
                    List<CascaderString> c;
                    if (resultSchoolIds == null) {
                        c = null;
                    } else {
                        c = resultSchoolIds.stream()
                                .map(schoolId -> {
                                    String cityName = schoolIdToNameMap.get(schoolId);
                                    return new CascaderString(schoolId + "", cityName, null);
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
