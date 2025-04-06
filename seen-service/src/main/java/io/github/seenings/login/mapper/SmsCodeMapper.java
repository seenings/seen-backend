package io.github.seenings.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.login.entity.SmsCode;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 短信验证码列表 Mapper 接口
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
@Mapper
public interface SmsCodeMapper extends BaseMapper<SmsCode> {}
