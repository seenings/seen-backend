package io.github.seenings.apply.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import io.github.seenings.apply.http.HttpUserApplyAgreeService;
import io.github.seenings.apply.service.UserApplyAgreeService;

/**
 * UserApplyAgreeController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RestController
@AllArgsConstructor
public class UserApplyAgreeController implements HttpUserApplyAgreeService {

    private UserApplyAgreeService userApplyAgreeService;

    @Override
    public Map<Integer, LocalDateTime> applyIdToAgreeTime( Set<Integer> applyIds) {
        return userApplyAgreeService.applyIdToAgreeTime(applyIds);
    }

    @Override
    public Integer set(  Integer applyId,  LocalDateTime agreeTime) {
        return userApplyAgreeService.set(applyId, agreeTime);
    }

}
