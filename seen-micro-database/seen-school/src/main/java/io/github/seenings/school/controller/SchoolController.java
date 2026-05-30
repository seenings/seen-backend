package io.github.seenings.school.controller;

import io.github.seenings.school.http.HttpSchoolService;
import io.github.seenings.school.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SchoolController
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@RestController
@AllArgsConstructor
public class SchoolController implements HttpSchoolService {
    private SchoolService schoolService;

    @Override
    public Map<Integer, String> idToSchoolName(@RequestBody Set<Integer> ids) {
        return schoolService.idToSchoolName(ids);
    }

    @Override
    public Map<String, List<Integer>> provinceCodeToSchoolId(@RequestBody Set<String> provinceCodes) {
        return schoolService.provinceCodeToSchoolId(provinceCodes);
    }

    @Override
    public Map<Integer, String> schoolIdToProvinceCode(@RequestBody Set<Integer> schoolIds) {
        return schoolService.schoolIdToProvinceCode(schoolIds);
    }
}
