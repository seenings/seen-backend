package io.github.seenings.chat.http;

import io.github.seenings.chat.model.ChatUser;
import io.github.seenings.common.model.ResultPage;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpChatUserService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_CHAT,
        path = FEIGN_VERSION + "chat/chat",
        contextId = "HttpChatUserService")
public interface HttpChatUserService {
    /**
     * 分页获取聊天列表
     *
     * @param userId  登录用户
     * @param current 当前页
     * @param size    页大小
     * @return 聊天列表
     */
    @PostMapping("page")
    ResultPage<ChatUser> page(
            @RequestParam("userId") Long userId,
            @RequestParam("current") int current,
            @RequestParam("size") int size);

    /**
     * 获取朋友ID是否为朋友
     * @param friendUserIds 朋友ID
     * @param userId 用户ID
     * @return  朋友ID对应是否朋友
     */
    @PostMapping("friend-user-id-is-friend")
    Map<Long, Boolean> friendUserIdIsFriend(
            @RequestBody Set<Long> friendUserIds, @RequestParam("userId") Long userId);

    /**
     * 设置朋友关系
     * @param friendUserId  朋友ID
     * @param userId    用户ID
     * @return  设置成功
     */
    @PostMapping("set")
    boolean set(@RequestParam("friendUserId") Long friendUserId, @RequestParam("userId") Long userId);
}
