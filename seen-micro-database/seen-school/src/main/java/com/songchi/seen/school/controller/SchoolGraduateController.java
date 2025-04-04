package com.songchi.seen.school.controller;

import com.songchi.seen.school.http.HttpSchoolGraduateService;
import com.songchi.seen.school.service.SchoolGraduateService;
import com.songchi.seen.sys.constant.SeenConstant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * SchoolGraduateController
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@RequestMapping(SeenConstant.FEIGN_VERSION + "school/graduate")
@RestController
@AllArgsConstructor
public class SchoolGraduateController implements HttpSchoolGraduateService {

    private SchoolGraduateService schoolGraduateService;

    @Override
    @PostMapping("user-id-to-graduated")
    public Map<Long, Integer> userIdToGraduated(@RequestBody Set<Long> userIds) {
        return schoolGraduateService.userIdToGraduated(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("graduated") Integer graduated) {
        return schoolGraduateService.set(userId, graduated);
    }
}
