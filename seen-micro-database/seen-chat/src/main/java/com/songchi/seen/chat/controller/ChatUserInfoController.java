package com.songchi.seen.chat.controller;

import com.songchi.seen.chat.http.HttpChatUserService;
import com.songchi.seen.chat.model.ChatUser;
import com.songchi.seen.chat.service.ChatUserService;
import com.songchi.seen.common.model.ResultPage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * ChatUserController
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@RestController
@AllArgsConstructor
@RequestMapping(FEIGN_VERSION + "chat/chat")
public class ChatUserInfoController implements HttpChatUserService {
    private ChatUserService chatUserService;

    /**
     * 分页获取聊天列表
     *
     * @param userId  登录用户
     * @param current 当前页
     * @param size    页大小
     * @return 聊天列表
     */
    @Override
    @PostMapping("page")
    public ResultPage<ChatUser> page(@RequestParam("userId") Long userId, @RequestParam("current") int current, @RequestParam("size") int size) {
        return chatUserService.page(userId, current, size);
    }

    /**
     * 获取朋友ID是否为朋友
     *
     * @param friendUserIds 朋友ID
     * @param userId        用户ID
     * @return 朋友ID对应是否朋友
     */
    @Override
    @PostMapping("friend-user-id-is-friend")
    public Map<Long, Boolean> friendUserIdIsFriend(@RequestBody Set<Long> friendUserIds, @RequestParam("userId") Long userId) {

        return chatUserService.friendUserIdIsFriend(friendUserIds, userId);
    }

    /**
     * 设置朋友关系
     *
     * @param friendUserId 朋友ID
     * @param userId       用户ID
     * @return 设置成功
     */
    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("friendUserId") Long friendUserId, @RequestParam("userId") Long userId) {
        return chatUserService.set(friendUserId, userId);
    }
}
