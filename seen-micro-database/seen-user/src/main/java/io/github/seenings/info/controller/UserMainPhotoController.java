package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserMainPhotoService;
import io.github.seenings.info.service.UserMainPhotoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserMainPhotoController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-main-photo")
public class UserMainPhotoController implements HttpUserMainPhotoService {

    @Resource
    private UserMainPhotoService userMainPhotoService;

    @Override
    @PostMapping("user-id-photo-id")
    public Map<Long, Integer> userIdPhotoId(@RequestBody Set<Long> userIds) {
        return userMainPhotoService.userIdPhotoId(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("photoId") Integer photoId) {
        return userMainPhotoService.set(userId, photoId);
    }
}
