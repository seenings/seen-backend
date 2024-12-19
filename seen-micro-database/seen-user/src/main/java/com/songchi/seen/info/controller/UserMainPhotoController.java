package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserMainPhotoService;
import com.songchi.seen.info.service.UserMainPhotoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

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
    public Map<Integer, Integer> userIdPhotoId(@RequestBody Set<Integer> userIds) {
        return userMainPhotoService.userIdPhotoId(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Integer userId, @RequestParam("photoId") Integer photoId) {
        return userMainPhotoService.set(userId, photoId);
    }
}
