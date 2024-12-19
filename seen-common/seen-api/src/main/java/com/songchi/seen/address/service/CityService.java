package com.songchi.seen.address.service;

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

    Map<String, Integer> cityCodeToCityId(Set<String> cityCodes);
}
