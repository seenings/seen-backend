package io.github.seenings.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author chixuehui
 */
@Builder
@Data
public class Cascader {
    private int v;
    private String t;
    private List<Cascader> c;
}
