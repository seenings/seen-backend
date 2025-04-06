package io.github.seenings.apply.controller;

import io.github.seenings.apply.http.HttpUserApplyRefuseService;
import io.github.seenings.apply.service.UserApplyRefuseService;
import io.github.seenings.sys.constant.SeenConstant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * UserApplyAgreeController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "chat/apply-refuse")
public class UserApplyRefuseController implements HttpUserApplyRefuseService {

    @Resource
    private UserApplyRefuseService userApplyRefuseService;


    @Override
    @PostMapping("apply-id-to-refuse-time")
    public Map<Integer, LocalDateTime> applyIdToRefuseTime(@RequestBody Set<Integer> applyIds ) {
        return userApplyRefuseService.applyIdToRefuseTime(applyIds);
    }

    @Override
    @PostMapping("add")
    public Integer add(@RequestParam("applyId") Integer applyId, @RequestParam("textId") Integer textId) {
        return userApplyRefuseService.add(applyId, textId);
    }

}
