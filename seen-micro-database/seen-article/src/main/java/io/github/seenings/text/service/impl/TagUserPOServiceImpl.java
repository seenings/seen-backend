package io.github.seenings.text.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.text.po.TagUserPO;
import io.github.seenings.text.service.TagUserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TagUserPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@Mapper
interface TagUserPOMapper extends BaseMapper<TagUserPO> {}

@Service
public class TagUserPOServiceImpl extends ServiceImpl<TagUserPOMapper, TagUserPO> implements TagUserService {

    /**
     * 保存用户标签
     * @param userId    用户ID
     * @param tagIds    标签ID
     * @return  用户标签的记录ID
     */
    @Override
    public List<Integer> deleteAndSave(Long userId, List<Integer> tagIds) {
        remove(new QueryWrapper<TagUserPO>().lambda().eq(TagUserPO::getUserId, userId));
        return tagIds.stream()
                .map(tagId -> {
                    TagUserPO po = new TagUserPO().setUserId(userId).setTagId(tagId);
                    save(po);
                    return po.getId();
                })
                .collect(Collectors.toList());
    }

    /**
     * 根据用户ID获取标签ID
     *
     * @param userIds 用户ID
     * @return 用户ID对应标签ID
     */
    @Override
    public Map<Long, List<Integer>> userIdToTagId(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<TagUserPO>()
                                .lambda()
                                .in(TagUserPO::getUserId, subs)
                                .select(TagUserPO::getUserId, TagUserPO::getTagId))
                        .stream())
                .collect(Collectors.groupingBy(
                        TagUserPO::getUserId, Collectors.mapping(TagUserPO::getTagId, Collectors.toList())));
    }

    /**
     * 根据标签ID获取用户ID
     *
     * @param tagIds 标签ID
     * @return 标签ID对应用户ID
     */
    @Override
    public Map<Integer, Set<Long>> tagIdToUserId(Set<Integer> tagIds) {
        List<Integer> list = CollUtil.valueIsNullToList(tagIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<TagUserPO>()
                                .lambda()
                                .in(TagUserPO::getTagId, subs)
                                .select(TagUserPO::getUserId, TagUserPO::getTagId))
                        .stream())
                .collect(Collectors.groupingBy(
                        TagUserPO::getTagId, Collectors.mapping(TagUserPO::getUserId, Collectors.toSet())));
    }
}
