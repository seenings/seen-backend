package io.github.seenings.apply.controller;

import io.github.seenings.apply.http.HttpUserApplyLookService;
import io.github.seenings.apply.service.UserApplyLookService;
import io.github.seenings.sys.constant.SeenConstant;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * UserApplyLookController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "chat/apply-look")
public class UserApplyLookController implements HttpUserApplyLookService {

    @Resource
    private UserApplyLookService userApplyLookService;

    @Override
    @PostMapping("apply-id-to-look-time")
    public Map<Integer, LocalDateTime> applyIdToLookTime(@RequestBody Set<Integer> applyIds) {
        return userApplyLookService.applyIdToLookTime(applyIds);
    }

    @Override
    @PostMapping("set")
    public Integer set(@RequestParam("applyId") Integer applyId, @RequestParam("lookTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lookTime) {
        return userApplyLookService.set(applyId, lookTime);
    }
}
