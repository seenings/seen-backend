package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserCurrentResidenceService;
import io.github.seenings.info.service.UserCurrentResidenceService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserCurrentResidenceController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
public class UserCurrentResidenceController implements HttpUserCurrentResidenceService {

    @Resource
    private UserCurrentResidenceService userCurrentResidenceService;

    @Override
    public Map<Long, Integer> userIdToCityId(Set<Long> userIds) {
        return userCurrentResidenceService.userIdToCityId(userIds);
    }

    @Override
    public Map<Long, Integer> userIdToProvinceId(Set<Long> userIds) {
        return userCurrentResidenceService.userIdToProvinceId(userIds);
    }

    @Override
    public boolean set(Long userId, Integer provinceId, Integer cityId) {
        return userCurrentResidenceService.set(userId, provinceId, cityId);
    }

    @Override
    public List<Long> currentResidenceCityToUserId(
            Integer cityId,
            int current, int size) {
        return userCurrentResidenceService.currentResidenceCityToUserId(cityId, current, size);
    }
}
