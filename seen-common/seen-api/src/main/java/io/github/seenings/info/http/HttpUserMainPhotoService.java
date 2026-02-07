package io.github.seenings.info.http;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * HttpUserMainPhotoService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@HttpExchange( SeenConstant.FEIGN_VERSION + "user/user-main-photo")
public interface HttpUserMainPhotoService {
    @PostExchange("user-id-photo-id")
    Map<Long, Integer> userIdPhotoId(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("photoId") Integer photoId);
}
