package com.songchi.seen.apply.controller;

import com.songchi.seen.apply.http.HttpUserApplyRefuseService;
import com.songchi.seen.apply.service.UserApplyRefuseService;
import com.songchi.seen.sys.constant.SeenConstant;
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
