package io.github.seenings.sys.service.impl;

import io.github.seenings.common.model.Env;
import io.github.seenings.sys.config.SeenConfig;
import io.github.seenings.sys.service.SysService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class SysServiceImpl implements SysService {

    @Resource
    private SeenConfig seenConfig;

    @Override
    public boolean dev() {
        Env env = seenConfig.getEnvConfig().getEnv();
        return Env.isDev(env);
    }

    /**
     * 是否是生产环境
     *
     * @return 是否
     */
    @Override
    public boolean isProd() {
        return Env.isProd(seenConfig.getEnvConfig().getEnv());
    }
}
