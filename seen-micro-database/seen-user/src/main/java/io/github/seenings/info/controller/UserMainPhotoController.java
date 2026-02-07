package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserMainPhotoService;
import io.github.seenings.info.service.UserMainPhotoService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserMainPhotoController implements HttpUserMainPhotoService {

    private UserMainPhotoService userMainPhotoService;

    @Override
    public Map<Long, Integer> userIdPhotoId(Set<Long> userIds) {
        return userMainPhotoService.userIdPhotoId(userIds);
    }

    @Override
    public boolean set(Long userId, Integer photoId) {
        return userMainPhotoService.set(userId, photoId);
    }
}
