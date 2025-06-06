package io.github.seenings.common.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserIdAndToken {
    private int userId;
    private String token;
}
