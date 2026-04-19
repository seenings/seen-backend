package io.github.seenings.apply.controller;

import io.github.seenings.apply.http.HttpUserApplyRefuseService;
import io.github.seenings.apply.service.UserApplyRefuseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/// 拒绝申请
@RestController
@AllArgsConstructor
public class UserApplyRefuseController implements HttpUserApplyRefuseService {

    private UserApplyRefuseService userApplyRefuseService;


    @Override
    public Map<Integer, LocalDateTime> applyIdToRefuseTime(Set<Integer> applyIds) {
        return userApplyRefuseService.applyIdToRefuseTime(applyIds);
    }

    @Override
    public Integer add(Integer applyId, Integer textId) {
        return userApplyRefuseService.add(applyId, textId);
    }

}
