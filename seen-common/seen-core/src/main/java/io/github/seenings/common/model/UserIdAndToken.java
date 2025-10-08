package io.github.seenings.common.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserIdAndToken {
    /**
     * 用户ID
     */
    private long userId;
    /**
     * 令牌
     */
    private String token;
}
