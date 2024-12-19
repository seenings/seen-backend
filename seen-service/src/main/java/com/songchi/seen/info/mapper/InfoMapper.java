package com.songchi.seen.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songchi.seen.info.entity.Info;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
@Mapper
public interface InfoMapper extends BaseMapper<Info> {}
