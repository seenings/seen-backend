package com.songchi.seen.work.controller;

import com.songchi.seen.info.http.HttpWorkPositionService;
import com.songchi.seen.info.service.WorkPositionService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * WorkPositionController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/work-position")
public class WorkPositionController implements HttpWorkPositionService {

    @Resource
    private WorkPositionService workPositionService;

    @Override
    @PostMapping("position-id-to-position-name")
    public Map<Integer, String> positionIdToPositionName(@RequestBody Set<Integer> ids) {
        return workPositionService.positionIdToPositionName(ids);
    }

    @Override
    @PostMapping("exists")
    public boolean exists(@RequestParam("positionName") String positionName) {
        return workPositionService.exists(positionName);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("positionName") String positionName) {

        return workPositionService.set(positionName);
    }


    @Override
    @GetMapping("work-position")
    public Map<Integer, String> workPosition() {
        return workPositionService.workPosition();
    }
}
