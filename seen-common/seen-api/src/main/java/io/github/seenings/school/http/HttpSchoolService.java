package io.github.seenings.school.http;

import org.springframework.web.bind.annotation.RequestBody;
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
@HttpExchange(
        value = FEIGN_VERSION + "school/school")
public interface HttpSchoolService {
    @PostExchange("id-to-school-name")
    Map<Integer, String> idToSchoolName(@RequestBody Set<Integer> ids);

    @PostExchange("province-code-to-school-id")
    Map<String, List<Integer>> provinceCodeToSchoolId(@RequestBody Set<String> provinceCodes);

    @PostExchange("school-id-to-province-code")
    Map<Integer, String> schoolIdToProvinceCode(@RequestBody Set<Integer> schoolIds);
}
