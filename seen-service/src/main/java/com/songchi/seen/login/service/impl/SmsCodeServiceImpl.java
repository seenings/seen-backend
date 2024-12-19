package com.songchi.seen.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.login.entity.SmsCode;
import com.songchi.seen.login.mapper.SmsCodeMapper;
import com.songchi.seen.login.service.ISmsCodeService;
import com.songchi.seen.sys.service.SysService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信验证码列表 服务实现类
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
@Service
@AllArgsConstructor
public class SmsCodeServiceImpl extends ServiceImpl<SmsCodeMapper, SmsCode> implements ISmsCodeService {

    private SysService sysService;

    @Override
    public boolean validate(String phone, Integer smsId, Integer smsCode) {

        if (!sysService.isProd()) {
            return true;
        }
        Long integer = this.baseMapper.selectCount(new QueryWrapper<SmsCode>().lambda()
                .eq(SmsCode::getPhone, phone)
                .eq(SmsCode::getSmsId, smsId)
                .eq(SmsCode::getSmsCode, smsCode)
        );
        return integer == 1;
    }
}
