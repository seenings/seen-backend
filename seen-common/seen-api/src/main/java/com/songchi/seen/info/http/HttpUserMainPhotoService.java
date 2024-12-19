package com.songchi.seen.info.http;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
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
    Map<Integer, Integer> userIdPhotoId(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Integer userId, @RequestParam("photoId") Integer photoId);
}
