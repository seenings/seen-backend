package io.github.seenings.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.seenings.login.entity.SmsCode;

/**
 * <p>
 * 短信验证码列表 服务类
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
public interface ISmsCodeService extends IService<SmsCode> {

    boolean validate(String phone, Integer smsId, Integer smsCode);
}
