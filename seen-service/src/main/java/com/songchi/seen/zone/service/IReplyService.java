package com.songchi.seen.zone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songchi.seen.zone.entity.Reply;
import com.songchi.seen.zone.model.ZoneReply;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 空间回复 服务类
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
public interface IReplyService extends IService<Reply> {

    Integer publishZoneComment(Integer zoneId, Long userId, String message);

    Integer publishReplyComment(Integer zoneId, Integer replyId, Long userId, String message);

    Map<Integer, Integer> idToZoneId(Set<Integer> ids);

    Map<Integer, Set<Integer>> zoneIdToReplyId(Set<Integer> zoneIds);

    Map<Integer, Reply> idToZoneReplyFirst(Set<Integer> ids);

    Map<Integer, Reply> idToZoneReplySecond(Set<Integer> ids);

    ZoneReply replyToZoneReply(Reply reply, Map<Long, String> userIdToUserNameMap, Map<Integer, Set<Integer>> zoneContentIdToTextIdMap, Map<Integer, String> textIdToTextMap);
}
