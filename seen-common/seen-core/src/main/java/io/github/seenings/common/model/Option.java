package io.github.seenings.common.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Option {
    private int v;
    private String t;
}
