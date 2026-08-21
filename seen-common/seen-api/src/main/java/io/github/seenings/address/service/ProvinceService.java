package io.github.seenings.address.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ProvinceService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface ProvinceService {
    Map<Integer, String> idToName(Set<Integer> ids);

    Map<String, Integer> provinceCodeToProvinceId(Set<String> provinceCodes);

    List<Map.Entry<String, String>> listAll();

    /// 获取省份名称
    ///
    /// @param provinceNames 省份名称
    /// @return 省份名称
    Set<String> toProvinceName(Set<String> provinceNames);
    /// 根据省会名获取省会代码
    ///
    /// @param provinceNames 省会名
    /// @return 省会名对应省会代码
    Map<String, String> provinceNameToProvinceCode(Set<String> provinceNames);
}
