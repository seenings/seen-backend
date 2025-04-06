package io.github.seenings.zone.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.article.enumeration.ContentType;
import io.github.seenings.zone.entity.Content;
import io.github.seenings.zone.mapper.ContentMapper;
import io.github.seenings.zone.service.IContentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 空间内容 服务实现类
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements IContentService {

    /**
     * 根据空间ID获取内容ID
     *
     * @param zoneIds 空间ID
     * @return 空间ID对应内容ID
     */
    @Override
    public Map<Integer, Set<Integer>> zoneIdToContentIdIsImage(Set<Integer> zoneIds) {
        if (CollUtil.isEmpty(zoneIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(zoneIds), 100).parallelStream()
                .flatMap(subs -> list(new QueryWrapper<Content>().lambda()
                        .in(Content::getZoneId, subs).eq(Content::getContentTypeId,
                                ContentType.IMAGE.getIndex())
                ).stream()).collect(Collectors.groupingBy(Content::getZoneId,
                        Collectors.mapping(Content::getContentId, Collectors.toSet())));
    }

    /**
     * 根据空间ID获取内容ID，文本
     *
     * @param zoneIds 空间ID
     * @return 空间ID对应内容ID
     */
    @Override
    public Map<Integer, Set<Integer>> zoneIdToContentIdIsText(Set<Integer> zoneIds) {
        if (CollUtil.isEmpty(zoneIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(zoneIds), 100).parallelStream()
                .flatMap(subs -> list(new QueryWrapper<Content>().lambda()
                        .in(Content::getZoneId, subs).eq(Content::getContentTypeId,
                                ContentType.TEXT.getIndex())
                ).stream()).collect(Collectors.groupingBy(Content::getZoneId,
                        Collectors.mapping(Content::getContentId, Collectors.toSet())));
    }

    /**
     * 根据空间内容ID获取内容ID，文本
     *
     * @param zoneContentIds 空间内容ID
     * @return 空间内容ID对应内容ID
     */
    @Override
    public Map<Integer, Set<Integer>> zoneContentIdToTextId(Set<Integer> zoneContentIds) {
        if (CollUtil.isEmpty(zoneContentIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(zoneContentIds), 100).parallelStream()
                .flatMap(subs -> list(new QueryWrapper<Content>().lambda()
                        .in(Content::getId, subs).eq(Content::getContentTypeId,
                                ContentType.TEXT.getIndex())
                ).stream()).collect(Collectors.groupingBy(Content::getId,
                        Collectors.mapping(Content::getContentId, Collectors.toSet())));
    }
}
