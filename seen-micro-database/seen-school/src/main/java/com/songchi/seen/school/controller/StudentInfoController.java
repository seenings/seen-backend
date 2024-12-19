package com.songchi.seen.school.controller;

import com.songchi.seen.school.http.HttpStudentInfoService;
import com.songchi.seen.school.service.StudentInfoService;
import com.songchi.seen.sys.constant.SeenConstant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * StudentInfoController
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@RestController
@AllArgsConstructor
@RequestMapping(SeenConstant.FEIGN_VERSION + "school/student-info")
public class StudentInfoController implements HttpStudentInfoService {

    private StudentInfoService studentInfoService;

    @Override
    @PostMapping("user-id-school-id")
    public Map<Integer, Integer> userIdToSchoolId(@RequestBody Set<Integer> userIds) {
        return studentInfoService.userIdToSchoolId(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Integer userId, @RequestParam("schoolId") Integer schoolId) {

        return studentInfoService.set(userId, schoolId);
    }
}
