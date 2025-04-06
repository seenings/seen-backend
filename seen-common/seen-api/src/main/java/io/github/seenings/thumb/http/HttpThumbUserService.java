package io.github.seenings.thumb.http;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * HttpThumbUserService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        contextId = "HttpThumbUserService",
        path = SeenConstant.FEIGN_VERSION + "thumb/thumb")
public interface HttpThumbUserService {
    /**
     * 根据被点赞者获取是否关注
     * @param thumbedUserIds 被点赞者
     * @param thumbUserId   点赞者
     * @return  被点赞者对应是否点赞
     */
    @PostMapping("thumbed-user-id-to-true")
    Map<Long, Boolean> thumbedUserIdToTrue(
            @RequestBody Set<Long> thumbedUserIds, @RequestParam("thumbUserId") Long thumbUserId);

    @PostMapping("set")
    boolean set(@RequestParam("thumbedUserId") Long thumbedUserId, @RequestParam("thumbUserId") Long thumbUserId);
}
