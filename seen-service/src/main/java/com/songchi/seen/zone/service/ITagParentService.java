package com.songchi.seen.zone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songchi.seen.zone.entity.TagParent;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public interface ITagParentService extends IService<TagParent> {

    Map<String, Integer> tagParentNameToTagParentId(Collection<String> tagParentNames);
}
