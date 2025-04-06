package io.github.seenings.chat.service;

import io.github.seenings.chat.model.ChatUser;
import io.github.seenings.common.model.ResultPage;

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
    ResultPage<ChatUser> page(Long userId, int current, int size);

    Map<Long,Boolean> friendUserIdIsFriend(Set<Long> friendUserIds, Long userId);

    boolean set(Long friendUserId, Long userId);
}
