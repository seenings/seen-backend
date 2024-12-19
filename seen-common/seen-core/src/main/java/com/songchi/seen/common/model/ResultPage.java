package com.songchi.seen.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author chixuehui
 * @since 2022-02-19
 */
@Data
@Accessors(chain = true)
public class ResultPage<T> {
    /**
     * 当前页内容
     */
    private List<T> pageList;
    /**
     * 总数
     */
    private Integer total;

}
