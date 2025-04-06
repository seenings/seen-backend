package io.github.seenings.chat.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chixu
 * 2021/6/13 14:05
 */
@Data
@Accessors(chain = true)
public class TwoUser {
    private Integer fromUserId;
    private Long toUserId;


}
