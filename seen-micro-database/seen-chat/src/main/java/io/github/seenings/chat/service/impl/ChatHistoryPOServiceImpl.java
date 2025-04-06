package io.github.seenings.chat.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.article.enumeration.ContentType;
import io.github.seenings.article.util.ArticleEnumUtils;
import io.github.seenings.chat.model.ChatContentAndTime;
import io.github.seenings.chat.model.ChatMessage;
import io.github.seenings.chat.po.ChatHistoryPO;
import io.github.seenings.chat.service.ChatHistoryService;
import io.github.seenings.core.util.CollUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ChatHistoryPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-09
 */
@Mapper
interface ChatHistoryPOMapper extends BaseMapper<ChatHistoryPO> {
}

@Service
public class ChatHistoryPOServiceImpl extends ServiceImpl<ChatHistoryPOMapper, ChatHistoryPO>
        implements ChatHistoryService {

    /**
     * 根据发送方用户和接收方用户获取聊天记录ID
     *
     * @param fromUserId 发送方
     * @param toUserId   接收方
     * @param current    当前页
     * @param size       页大小
     * @return 聊天记录ID
     */
    @Override
    public List<Integer> fromUserIdToId(Long fromUserId, Long toUserId, int current, int size) {

        return page(new Page<>(current, size), new QueryWrapper<ChatHistoryPO>()
                .lambda().eq(ChatHistoryPO::getFromUserId, fromUserId)
                .eq(ChatHistoryPO::getToUserId, toUserId)
                .orderByDesc(ChatHistoryPO::getSendTime)
                .select(ChatHistoryPO::getId)
        ).getRecords().stream().map(ChatHistoryPO::getId).collect(Collectors.toList());
    }


    /**
     * 根据ID获取聊天内容和时间
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应聊天内容和时间
     */
    @Override
    public Map<Integer, ChatContentAndTime> idToChatContentAndTime(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<ChatHistoryPO>()
                        .lambda()
                        .in(ChatHistoryPO::getId, subs)
                        .select(
                                ChatHistoryPO::getId,
                                ChatHistoryPO::getSendTime,
                                ChatHistoryPO::getContentId,
                                ChatHistoryPO::getContentTypeId))
                        .stream())
                .collect(Collectors.toMap(
                        ChatHistoryPO::getId,
                        n -> new ChatContentAndTime(
                                ArticleEnumUtils.indexToContentTypeEnum(n.getContentTypeId()),
                                n.getContentId(),
                                n.getSendTime())));
    }

    /**
     * 根据聊天记录ID获取发送方ID
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应发送方ID
     */
    @Override
    public Map<Integer, Long> idToFromUserId(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<ChatHistoryPO>()
                        .lambda()
                        .in(ChatHistoryPO::getId, subs)
                        .select(
                                ChatHistoryPO::getId,
                                ChatHistoryPO::getFromUserId))
                        .stream())
                .collect(Collectors.toMap(
                        ChatHistoryPO::getId,
                        ChatHistoryPO::getFromUserId));
    }

    /**
     * 根据聊天记录ID获取发出时间
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应发出时间
     */
    @Override
    public Map<Integer, LocalDateTime> idToSendTime(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<ChatHistoryPO>()
                        .lambda()
                        .in(ChatHistoryPO::getId, subs)
                        .select(
                                ChatHistoryPO::getId,
                                ChatHistoryPO::getSendTime))
                        .stream())
                .collect(Collectors.toMap(
                        ChatHistoryPO::getId,
                        ChatHistoryPO::getSendTime));
    }
    /**
     * 根据聊天记录ID获取是否发出
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应是否发出
     */
    @Override
    public Map<Integer, Boolean> idToIsSent(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<ChatHistoryPO>()
                        .lambda()
                        .in(ChatHistoryPO::getId, subs)
                        .select(
                                ChatHistoryPO::getId,
                                ChatHistoryPO::isSent))
                        .stream())
                .collect(Collectors.toMap(
                        ChatHistoryPO::getId,
                        ChatHistoryPO::isSent));
    }

    /**
     * 根据聊天记录ID获取接收方ID
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应接收方ID
     */
    @Override
    public Map<Integer, Long> idToToUserId(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<ChatHistoryPO>()
                        .lambda()
                        .in(ChatHistoryPO::getId, subs)
                        .select(
                                ChatHistoryPO::getId,
                                ChatHistoryPO::getToUserId))
                        .stream())
                .collect(Collectors.toMap(
                        ChatHistoryPO::getId,
                        ChatHistoryPO::getToUserId));
    }

    @Override
    public boolean add(ChatMessage chatMessage, LocalDateTime sendTime, boolean sent) {
        Integer contentId;
        switch (ArticleEnumUtils.indexToContentTypeEnum(chatMessage.contentTypeId())) {
            case TEXT, VOICE, IMAGE -> contentId = chatMessage.contentId();
            default -> contentId = null;
        }
        ChatHistoryPO history = new ChatHistoryPO();
        history.setFromUserId(chatMessage.fromUserId());
        history.setToUserId(chatMessage.toUserId());
        history.setSent(sent);
        history.setContentId(contentId);
        history.setContentTypeId(chatMessage.contentTypeId());
        history.setSendTime(chatMessage.sendTime());
        history.setUpdateTime(LocalDateTime.now());
        return save(history);
    }

    @Override
    public Integer add(ContentType contentType, Integer contentId,
                       Long fromUserId,
                       Long toUserId, Boolean sent, LocalDateTime sendTime) {
        ChatHistoryPO history = new ChatHistoryPO();
        history.setFromUserId(fromUserId);
        history.setToUserId(toUserId);
        history.setSent(sent);
        history.setContentId(contentId);
        history.setContentTypeId(contentType.getIndex());
        history.setSendTime(sendTime);
        history.setUpdateTime(LocalDateTime.now());
        save(history);
        return history.getId();
    }

    @Override
    public boolean setSent(Integer id) {
        return update(new ChatHistoryPO().setSent(true).setUpdateTime(LocalDateTime.now()), new QueryWrapper<ChatHistoryPO>().lambda()
                .eq(ChatHistoryPO::getId, id));

    }

}
