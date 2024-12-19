package com.songchi.seen.sys.service.impl;

import com.songchi.seen.common.model.Env;
import com.songchi.seen.sys.config.SeenConfig;
import com.songchi.seen.sys.service.SysService;
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
