package com.songchi.seen.zone.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.zone.entity.TagNeed;
import com.songchi.seen.zone.mapper.TagNeedMapper;
import com.songchi.seen.zone.service.ITagNeedService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TagNeedServiceImpl extends ServiceImpl<TagNeedMapper, TagNeed>
        implements ITagNeedService {


    /**
     * 根据用户ID获取意向标签
     *
     * @param userIds 用户ID
     * @return 用户ID对应意向标签
     */
    @Override
    public Map<Integer, Set<Integer>> userIdToNeedTagId(Set<Integer> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(userIds), 100).parallelStream().distinct().flatMap(subs ->
                list(new QueryWrapper<TagNeed>().lambda().in(TagNeed::getUserId, subs)).stream()
        ).filter(n -> n.getUserId() != null).collect(Collectors.groupingBy
                (TagNeed::getUserId, Collectors.mapping(TagNeed::getNeedTagId, Collectors.toSet())));

    }
}




