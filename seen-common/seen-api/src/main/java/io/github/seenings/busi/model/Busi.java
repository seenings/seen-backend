package io.github.seenings.busi.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 业务
 */
@Data
@Accessors(chain = true)
public class Busi {

    /**
     * 业务ID
     */
    private Long busiId;

    /**
     * 业务类型ID
     */
    private Integer busiTypeId;
    /**
     * 业务时间
     */
    private LocalDateTime busiTime;
}
