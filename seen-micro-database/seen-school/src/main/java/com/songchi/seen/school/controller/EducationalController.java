package com.songchi.seen.school.controller;

import com.songchi.seen.school.enumeration.Education;
import com.songchi.seen.school.http.HttpEducationalService;
import com.songchi.seen.school.service.EducationalService;
import com.songchi.seen.sys.constant.SeenConstant;
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
    public Map<Integer, Integer> userIdToEducational(@RequestBody Set<Integer> userIds) {
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
    public boolean set(@RequestParam("userId") Integer userId, @RequestParam("education") Education education) {
        return educationalService.set(userId, education);
    }
}
