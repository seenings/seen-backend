package com.songchi.seen.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songchi.seen.login.entity.SmsCode;
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
