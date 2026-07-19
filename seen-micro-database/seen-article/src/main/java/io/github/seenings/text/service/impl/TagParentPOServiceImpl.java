package io.github.seenings.text.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.tag.service.TagParentService;
import io.github.seenings.text.po.TagParentPO;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TagParentPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-02-13
 */
@Mapper
interface TagParentPOMapper extends BaseMapper<TagParentPO> {}

@AllArgsConstructor
@Repository
public class TagParentPOServiceImpl   implements TagParentService {
    private TagParentPOMapper tagParentPOMapper;

    @Override
    public Map<Integer, String> toParentIdToParentName() {
        List<TagParentPO> list =
                tagParentPOMapper.selectList(new QueryWrapper<TagParentPO>().lambda().select(TagParentPO::getId, TagParentPO::getTagName));
        return list.stream().collect(Collectors.toMap(TagParentPO::getId, TagParentPO::getTagName));
    }
}
