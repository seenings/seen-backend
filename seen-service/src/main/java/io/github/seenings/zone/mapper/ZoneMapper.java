package io.github.seenings.zone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.zone.entity.Zone;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 空间 Mapper 接口
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Mapper
public interface ZoneMapper extends BaseMapper<Zone> {}
