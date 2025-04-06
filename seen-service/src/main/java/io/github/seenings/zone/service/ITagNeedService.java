package io.github.seenings.zone.service;

import io.github.seenings.zone.entity.TagNeed;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface ITagNeedService extends IService<TagNeed> {

    Map<Long, Set<Integer>> userIdToNeedTagId(Set<Long> userIds);
}
