package com.songchi.seen.chat.service;

import com.songchi.seen.chat.model.ChatUser;
import com.songchi.seen.common.model.ResultPage;

import java.util.Map;
import java.util.Set;

/**
 * ChatUserService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface ChatUserService {
    /**
     * 分页获取聊天列表
     *
     * @param userId  登录用户
     * @param current 当前页
     * @param size    页大小
     * @return 聊天列表
     */
    ResultPage<ChatUser> page(Integer userId, int current, int size);

    Map<Integer,Boolean> friendUserIdIsFriend(Set<Integer> friendUserIds, Integer userId);

    boolean set(Integer friendUserId, Integer userId);
}
