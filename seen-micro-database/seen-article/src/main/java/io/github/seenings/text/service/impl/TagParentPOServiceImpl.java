package io.github.seenings.text.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.tag.service.TagParentService;
import io.github.seenings.text.po.TagParentPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

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

@Service
public class TagParentPOServiceImpl extends ServiceImpl<TagParentPOMapper, TagParentPO> implements TagParentService {

    @Override
    public Map<Integer, String> toParentIdToParentName() {
        List<TagParentPO> list =
                list(new QueryWrapper<TagParentPO>().lambda().select(TagParentPO::getId, TagParentPO::getTagName));
        return list.stream().collect(Collectors.toMap(TagParentPO::getId, TagParentPO::getTagName));
    }
}
