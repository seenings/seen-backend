package io.github.seenings.address.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CityService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface CityService {
    Map<Integer, String> idToName(Set<Integer> ids);

    Map<String, String> codeToName(Set<String> codes);

    Map<String, List<String>> provinceCodeToCode(Set<String> provinceCodes);

    /// 获取城市名称
    ///
    /// @param cityNames 城市名称
    /// @return 城市名称
    Set<String> toCityName(Set<String> cityNames);

    Map<String, Integer> cityCodeToCityId(Set<String> cityCodes);
    /// 根据城市名获取省会代码
    ///
    /// @param cityNames 城市名
    /// @return 城市名对应省会代码
    Map<String, String> cityNameToProvinceCode(Set<String> cityNames);
}
