package com.songchi.seen.zone.service;

import com.songchi.seen.zone.model.UserTag;

import java.util.Map;
import java.util.Set;

/**
 * 标签
 * @author chixuehui
 * @since 2022-02-19
 */
public interface ITagService {

    Map<String, String> tagParentNameToTagName(Set<String> tagParentNames, Integer userId);

    Map<Integer, UserTag> userIdToUserTag(Set<Integer> userIds);
}
