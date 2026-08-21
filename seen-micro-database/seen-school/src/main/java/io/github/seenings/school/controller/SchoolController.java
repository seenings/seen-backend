package io.github.seenings.school.controller;

import io.github.seenings.common.exception.SeenRuntimeException;
import io.github.seenings.school.http.HttpSchoolService;
import io.github.seenings.school.model.School;
import io.github.seenings.school.util.SchoolExcelReaderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SchoolController
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@Slf4j
@RestController
@AllArgsConstructor
public class SchoolController implements HttpSchoolService {

    @Override
    public Map<String, String> idToSchoolName(@RequestBody Set<String> ids) {

        //TODO 做缓存，做索引
        List<School> schools = toSchool();

        return schools.stream()
                .filter(school -> ids.contains(school.getSchoolCode())
                ).map(school -> Map.entry(school.getSchoolCode(), school.getSchoolName()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /// 根据所在地获取学校标识符
    ///
    /// @param locations 所在地
    /// @return 所在地对应学校标识符
    @Override
    public Map<String, Set<String>> locationToSchoolCode(Set<String> locations) {
        //TODO 做缓存，做索引
        List<School> schools = toSchool();
        return schools.stream()
                .filter(school -> locations.contains(school.getLocation())
                ).map(school -> Map.entry(school.getLocation(), school.getSchoolCode()))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));
    }

    /// 根据学校标识符获取所在地
    ///
    /// @param schoolCodes 学校标识符
    /// @return 学校标识符对应所在地
    @Override
    public Map<String, String> schoolCodeToLocation(Set<String> schoolCodes) {
        //TODO 做缓存，做索引
        List<School> schools = toSchool();
        return schools.stream()
                .filter(school -> schoolCodes.contains(school.getSchoolCode())
                ).map(school -> Map.entry(school.getSchoolCode(), school.getLocation()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }





    /// 获取所有学校的信息
    ///
    /// @return 所有学校的信息
    @Override
    public List<School> toSchool() {

        try (InputStream inputStream = this.getClass().
                getResourceAsStream("/data/school/W020260618416094865984.xls")) {
            return SchoolExcelReaderUtil.read(inputStream);
        } catch (IOException e) {
            log.error("", e);
            throw new SeenRuntimeException(e);
        }
    }


    @Override
    public List<School> uploadExcelAll(@RequestParam MultipartFile file) {

        try {
            return SchoolExcelReaderUtil.read(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
