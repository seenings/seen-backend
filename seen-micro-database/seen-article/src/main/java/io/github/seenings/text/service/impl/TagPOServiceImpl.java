package io.github.seenings.text.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.text.po.TagPO;
import io.github.seenings.text.service.TagService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TagPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@Mapper
interface TagPOMapper extends BaseMapper<TagPO> {}

@Service
public class TagPOServiceImpl extends ServiceImpl<TagPOMapper, TagPO> implements TagService {

    /**
     * 保存标签
     * @param parentTagId  父级标签ID
     * @param tagName  标签名
     * @return  标签名ID
     */
    @Override
    public Integer saveAndReturnId(Integer parentTagId, String tagName) {
        TagPO po = new TagPO().setTagName(tagName).setParentTagId(parentTagId);
        save(po);
        return po.getId();
    }

    /**
     * 根据标签ID对应标签名
     *
     * @param tagIds 标签ID
     * @return 标签ID对应标签名
     */
    @Override
    public Map<Integer, String> tagIdToTagName(Set<Integer> tagIds) {
        List<Integer> list = CollUtil.valueIsNullToList(tagIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<TagPO>()
                                .lambda()
                                .in(TagPO::getId, subs)
                                .select(TagPO::getId, TagPO::getTagName))
                        .stream())
                .collect(Collectors.toMap(TagPO::getId, TagPO::getTagName, (o1, o2) -> o2));
    }

    /**
     * 根据标签ID获取标签父ID
     *
     * @param tagIds 标签ID
     * @return 标签ID对应标签父ID
     */
    @Override
    public Map<Integer, Integer> tagIdToTagParentId(Set<Integer> tagIds) {
        if (cn.hutool.core.collection.CollUtil.isEmpty(tagIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(tagIds), 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<TagPO>()
                                .lambda()
                                .in(TagPO::getId, subs)
                                .select(TagPO::getParentTagId, TagPO::getId))
                        .stream())
                .collect(Collectors.toMap(TagPO::getId, TagPO::getParentTagId, (o1, o2) -> o2));
    }

    @Override
    public Map<Integer, List<Integer>> toParentIdToTagId() {
        List<TagPO> list = list(new QueryWrapper<TagPO>().lambda().select(TagPO::getParentTagId, TagPO::getId));

        return list.stream()
                .collect(Collectors.groupingBy(
                        TagPO::getParentTagId, Collectors.mapping(TagPO::getId, Collectors.toList())));
    }

    @Override
    public Map<Integer, String> toTagIdToTagName() {
        List<TagPO> list = list(new QueryWrapper<TagPO>().lambda().select(TagPO::getId, TagPO::getTagName));
        return list.stream().collect(Collectors.toMap(TagPO::getId, TagPO::getTagName));
    }
}
