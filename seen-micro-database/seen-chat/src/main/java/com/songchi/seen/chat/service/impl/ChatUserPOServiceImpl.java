package com.songchi.seen.chat.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.chat.model.ChatUser;
import com.songchi.seen.chat.po.ChatUserPO;
import com.songchi.seen.chat.service.ChatUserService;
import com.songchi.seen.common.model.ResultPage;
import com.songchi.seen.core.util.CollUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ChatUserPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Mapper
interface ChatUserPOMapper extends BaseMapper<ChatUserPO> {}

@Service
public class ChatUserPOServiceImpl extends ServiceImpl<ChatUserPOMapper, ChatUserPO> implements ChatUserService {

    /**
     * 分页获取聊天列表
     * TODO，根据策略获取最新的聊天好友
     * @param userId  登录用户
     * @param current 当前页
     * @param size    页大小
     * @return 聊天列表
     */
    @Override
    public ResultPage<ChatUser> page(Long userId, int current, int size) {
        Page<ChatUserPO> chatUsers = page(
                new Page<>(current, size),
                new QueryWrapper<ChatUserPO>()
                        .lambda()
                        .eq(ChatUserPO::getUserId, userId)
                        .orderByDesc(ChatUserPO::getUpdateTime));

        List<ChatUser> chatUserList = chatUsers.getRecords().stream()
                .map(n -> new ChatUser(n.getId(), n.getUserId(), n.getFriendUserId(), n.getUpdateTime()))
                .toList();
        return new ResultPage<ChatUser>().setPageList(chatUserList).setTotal(Math.toIntExact(chatUsers.getTotal()));
    }

    /**
     * 获取朋友ID是否为朋友
     * @param friendUserIds 朋友ID
     * @param userId 用户ID
     * @return  朋友ID对应是否朋友
     */
    @Override
    public Map<Long, Boolean> friendUserIdIsFriend(Set<Long> friendUserIds, Long userId) {
        List<Long> list = CollUtil.valueIsNullToList(friendUserIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        Map<Long, Boolean> friendUserIdToIsFriendMap = ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<ChatUserPO>()
                                .lambda()
                                .in(ChatUserPO::getFriendUserId, subs)
                                .eq(ChatUserPO::getUserId, userId)
                                .select(ChatUserPO::getFriendUserId))
                        .stream())
                .collect(Collectors.toMap(ChatUserPO::getFriendUserId, n -> true, (o1, o2) -> o2));
        return friendUserIds.stream()
                .parallel()
                .collect(Collectors.toMap(
                        Function.identity(),
                        n -> {
                            Boolean is = friendUserIdToIsFriendMap.get(n);
                            return is != null && is;
                        },
                        (o1, o2) -> o2));
    }

    /**
     * 设置朋友关系
     * @param friendUserId  朋友ID
     * @param userId    用户ID
     * @return  设置成功
     */
    @Override
    public boolean set(Long friendUserId, Long userId) {
        Boolean isFriend = friendUserIdIsFriend(Collections.singleton(friendUserId), userId)
                .get(friendUserId);
        if (!isFriend) {
            ChatUserPO po = new ChatUserPO()
                    .setUserId(userId)
                    .setFriendUserId(friendUserId)
                    .setUpdateTime(LocalDateTime.now());
            return save(po);
        } else {
            return false;
        }
    }
}
