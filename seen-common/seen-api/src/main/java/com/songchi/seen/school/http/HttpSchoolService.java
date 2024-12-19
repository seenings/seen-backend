package com.songchi.seen.school.http;

import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpSchoolService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_SCHOOL,
        path = FEIGN_VERSION + "school/school",
        contextId = "HttpSchoolService")
public interface HttpSchoolService {
    @PostMapping("id-to-school-name")
    Map<Integer, String> idToSchoolName(@RequestBody Set<Integer> ids);

    @PostMapping("province-code-to-school-id")
    Map<String, List<Integer>> provinceCodeToSchoolId(@RequestBody Set<String> provinceCodes);

    @PostMapping("school-id-to-province-code")
    Map<Integer, String> schoolIdToProvinceCode(@RequestBody Set<Integer> schoolIds);
}
