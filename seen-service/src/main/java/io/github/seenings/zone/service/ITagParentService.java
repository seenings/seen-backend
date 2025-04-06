package io.github.seenings.zone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.seenings.zone.entity.TagParent;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public interface ITagParentService extends IService<TagParent> {

    Map<String, Integer> tagParentNameToTagParentId(Collection<String> tagParentNames);
}
