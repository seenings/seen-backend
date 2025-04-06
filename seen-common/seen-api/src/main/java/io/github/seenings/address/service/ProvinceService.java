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
}
