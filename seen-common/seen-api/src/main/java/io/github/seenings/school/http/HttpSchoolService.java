package io.github.seenings.school.http;

import io.github.seenings.school.model.School;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpSchoolService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@HttpExchange(value = FEIGN_VERSION + "school/school")
public interface HttpSchoolService {
    @PostExchange("id-to-school-name")
    Map<String, String> idToSchoolName(@RequestBody Set<String> ids);

    /// 根据所在地获取学校标识符
    ///
    /// @param locations 所在地
    /// @return 所在地对应学校标识符
    @PostExchange("location-to-school-code")
    Map<String, Set<String>> locationToSchoolCode(@RequestBody Set<String> locations);

    /// 根据学校标识符获取所在地
    ///
    /// @param schoolCodes 学校标识符
    /// @return 学校标识符对应所在地
    @PostExchange("school-code-to-location")
    Map<String, String> schoolCodeToLocation(@RequestBody Set<String> schoolCodes);


    /// 获取所有学校的信息
    ///
    /// @return 所有学校的信息
    @PostExchange("to-school")
    List<School> toSchool();

    @PostExchange("upload-execl-all")
    List<School> uploadExcelAll(@RequestParam MultipartFile file);
}
