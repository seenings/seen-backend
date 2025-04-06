package io.github.seenings.school.controller;

import io.github.seenings.school.enumeration.Education;
import io.github.seenings.school.http.HttpEducationalService;
import io.github.seenings.school.service.EducationalService;
import io.github.seenings.sys.constant.SeenConstant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * EducationalController
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@RequestMapping(SeenConstant.FEIGN_VERSION + "school/educational")
@RestController
@AllArgsConstructor
public class EducationalController implements HttpEducationalService {

    private EducationalService educationalService;

    @Override
    @PostMapping("user-id-to-educational")
    public Map<Long, Integer> userIdToEducational(@RequestBody Set<Long> userIds) {
        return educationalService.userIdToEducational(userIds);
    }

    /**
     * 设置用户的学历
     * @param userId    用户
     * @param education 学历
     * @return  实际写入数据
     */
    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("education") Education education) {
        return educationalService.set(userId, education);
    }
}
