package io.github.seenings.zone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.zone.entity.TagNeed;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户需求的标签类型
 */
@Mapper
public interface TagNeedMapper extends BaseMapper<TagNeed> {}
