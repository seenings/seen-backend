package com.songchi.seen.zone.service;

import com.songchi.seen.zone.entity.TagNeed;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface ITagNeedService extends IService<TagNeed> {

    Map<Integer, Set<Integer>> userIdToNeedTagId(Set<Integer> userIds);
}
