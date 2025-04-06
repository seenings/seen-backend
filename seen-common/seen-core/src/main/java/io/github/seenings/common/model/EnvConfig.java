package io.github.seenings.common.model;

import lombok.Data;

/**
 * EnvConfig
 *
 */
@Data
public class EnvConfig {
    /**
     * 环境
     */
    private Env env = Env.PRD;
}
