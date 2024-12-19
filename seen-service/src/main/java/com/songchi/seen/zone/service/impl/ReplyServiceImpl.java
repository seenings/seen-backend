package com.songchi.seen.zone.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.text.http.HttpTextService;
import com.songchi.seen.zone.entity.Reply;
import com.songchi.seen.zone.mapper.ReplyMapper;
import com.songchi.seen.zone.model.ZoneReply;
import com.songchi.seen.zone.service.IReplyService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 空间回复 服务实现类
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

    @Resource
    private HttpTextService httpTextService;
    /**
     * 发表空间评论
     *
     * @param zoneId  空间ID
     * @param userId  用户ID
     * @param message 评论消息
     * @return 回复ID
     */
    @Override
    public Integer publishZoneComment(Integer zoneId, Integer userId, String message) {
        Integer zoneContentId = httpTextService.saveAndReturnId(message);
        Reply reply = new Reply();
        reply.setZoneId(zoneId);
        reply.setUserId(userId);
        reply.setZoneContentId(zoneContentId);
        save(reply);
        return reply.getId();
    }

    /**
     * 回复空间评论
     *
     * @param zoneId  空间ID
     * @param userId  用户ID
     * @param message 评论消息
     * @return 回复ID
     */
    @Override
    public Integer publishReplyComment(Integer zoneId, Integer replyId, Integer userId, String message) {
        Integer zoneContentId = httpTextService.saveAndReturnId(message);
        Reply reply = new Reply();
        reply.setZoneId(zoneId);
        reply.setUserId(userId);
        reply.setReplyId(replyId);
        reply.setZoneContentId(zoneContentId);
        save(reply);
        return reply.getId();
    }

    /**
     * 根据ID获取空间ID
     *
     * @param ids id
     * @return id对应空间ID
     */
    @Override
    public Map<Integer, Integer> idToZoneId(Set<Integer> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(ids), 500).stream().flatMap(subs ->
                list(new QueryWrapper<Reply>().lambda().in(Reply::getId, subs)
                        .select(Reply::getId, Reply::getZoneId)
                ).stream()).collect(Collectors.toMap(Reply::getId, Reply::getZoneId, (o1, o2) -> o2));
    }

    /**
     * 根据空间ID获取回复ID
     *
     * @param zoneIds 空间ID
     * @return 空间ID对应回复ID
     */
    @Override
    public Map<Integer, Set<Integer>> zoneIdToReplyId(Set<Integer> zoneIds) {
        if (CollUtil.isEmpty(zoneIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(zoneIds), 500).stream().flatMap(subs ->
                list(new QueryWrapper<Reply>().lambda().in(Reply::getZoneId, subs).select(Reply::getId, Reply::getZoneId)
                ).stream()).collect(Collectors.groupingBy(Reply::getZoneId,
                Collectors.mapping(Reply::getId, Collectors.toSet())));
    }

    /**
     * 根据ID获取第一层评论
     *
     * @param ids id
     * @return id对应回复
     */
    @Override
    public Map<Integer, Reply> idToZoneReplyFirst(Set<Integer> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(ids), 500).stream().flatMap(subs ->
                list(new QueryWrapper<Reply>().lambda().in(Reply::getId, subs)
                        .eq(Reply::getReplyId, 0)
                ).stream()).collect(Collectors.toMap(Reply::getId, Function.identity(), (o1, o2) -> o2));
    }

    /**
     * 根据ID获取第二层回复
     *
     * @param ids id
     * @return id对应回复
     */
    @Override
    public Map<Integer, Reply> idToZoneReplySecond(Set<Integer> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(ids), 500).stream().flatMap(subs ->
                list(new QueryWrapper<Reply>().lambda().in(Reply::getId, subs)
                        .ne(Reply::getReplyId, 0)
                ).stream()).collect(Collectors.toMap(Reply::getId, Function.identity(), (o1, o2) -> o2));
    }


    @Override
    public ZoneReply replyToZoneReply(Reply reply, Map<Integer, String> userIdToUserNameMap, Map<Integer, Set<Integer>> zoneContentIdToTextIdMap, Map<Integer, String> textIdToTextMap) {
        //第一层评论
        Integer userId = reply.getUserId();
        String userName = userIdToUserNameMap.get(userId);
        ZoneReply zoneReply = new ZoneReply().setId(reply.getId()).setReplyId(reply.getReplyId())
                .setUserName(userName);
        Integer zoneContentId = reply.getZoneContentId();
        Integer textId = zoneContentIdToTextIdMap.get(zoneContentId).stream().findFirst().orElse(null);
        if (textId != null) {
            String text = textIdToTextMap.get(textId);
            if (text != null) {
                zoneReply.setContent(text);
            }
        }
        return zoneReply;
    }
}
