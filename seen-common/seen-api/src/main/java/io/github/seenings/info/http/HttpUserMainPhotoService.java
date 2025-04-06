package io.github.seenings.info.http;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * HttpUserMainPhotoService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "user/user-main-photo",
        contextId = "HttpUserMainPhotoService")
public interface HttpUserMainPhotoService {
    @PostMapping("user-id-photo-id")
    Map<Long, Integer> userIdPhotoId(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("photoId") Integer photoId);
}
