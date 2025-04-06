package io.github.seenings.school.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SchoolService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
public interface SchoolService {
    Map<Integer, String> idToSchoolName(Set<Integer> ids);

    Map<String, List<Integer>> provinceCodeToSchoolId(Set<String> provinceCodes);

    Map<Integer, String> schoolIdToProvinceCode(Set<Integer> schoolIds);
}
