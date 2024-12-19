package com.songchi.seen.apply.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.songchi.seen.apply.http.HttpUserApplyAgreeService;
import com.songchi.seen.apply.service.UserApplyAgreeService;
import com.songchi.seen.sys.constant.SeenConstant;

import jakarta.annotation.Resource;

/**
 * UserApplyAgreeController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "chat/apply-agree")
public class UserApplyAgreeController implements HttpUserApplyAgreeService {

    @Resource
    private UserApplyAgreeService userApplyAgreeService;

    @Override
    @PostMapping("apply-id-to-agree-time")
    public Map<Integer, LocalDateTime> applyIdToAgreeTime(@RequestBody Set<Integer> applyIds) {
        return userApplyAgreeService.applyIdToAgreeTime(applyIds);
    }

    @Override
    @PostMapping("set")
    public Integer set(@RequestParam("applyId") Integer applyId,
            @RequestParam("agreeTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime agreeTime) {
        return userApplyAgreeService.set(applyId, agreeTime);
    }

}
