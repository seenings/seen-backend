package io.github.seenings.apply.controller;

import io.github.seenings.apply.http.HttpUserApplyLookService;
import io.github.seenings.apply.service.UserApplyLookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/// 查看申请
@RestController
@AllArgsConstructor
public class UserApplyLookController implements HttpUserApplyLookService {

    private UserApplyLookService userApplyLookService;

    @Override
    public Map<Integer, LocalDateTime> applyIdToLookTime(Set<Integer> applyIds) {
        return userApplyLookService.applyIdToLookTime(applyIds);
    }

    @Override
    public Integer set(Integer applyId, LocalDateTime lookTime) {
        return userApplyLookService.set(applyId, lookTime);
    }
}
