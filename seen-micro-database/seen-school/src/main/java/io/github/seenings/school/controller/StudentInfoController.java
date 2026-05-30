package io.github.seenings.school.controller;

import io.github.seenings.school.http.HttpStudentInfoService;
import io.github.seenings.school.service.StudentInfoService;
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
public class StudentInfoController implements HttpStudentInfoService {

    private StudentInfoService studentInfoService;

    @Override
    public Map<Long, Integer> userIdToSchoolId(@RequestBody Set<Long> userIds) {
        return studentInfoService.userIdToSchoolId(userIds);
    }

    @Override
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("schoolId") Integer schoolId) {

        return studentInfoService.set(userId, schoolId);
    }
}
