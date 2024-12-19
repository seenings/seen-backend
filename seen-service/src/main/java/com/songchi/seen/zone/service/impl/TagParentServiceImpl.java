package com.songchi.seen.zone.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.zone.entity.TagParent;
import com.songchi.seen.zone.mapper.TagParentMapper;
import com.songchi.seen.zone.service.ITagParentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TagParentServiceImpl extends ServiceImpl<TagParentMapper, TagParent> implements ITagParentService {

    /**
     * 根据父标签名字获取父标签ID
     *
     * @param tagParentNames 父标签名字
     * @return 父标签名字对应父标签ID
     */
    @Override
    public Map<String, Integer> tagParentNameToTagParentId(Collection<String> tagParentNames) {

        if (CollUtil.isEmpty(tagParentNames)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(tagParentNames), 100).parallelStream()
                .flatMap(subs -> list(new QueryWrapper<TagParent>().lambda().in(TagParent::getTagName, subs)).stream())
                .collect(Collectors.toMap(TagParent::getTagName, TagParent::getId, (o1, o2) -> o2));
    }
}
