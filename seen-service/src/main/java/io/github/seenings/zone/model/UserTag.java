package io.github.seenings.zone.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author chixh
 */
@Data
@Accessors(chain = true)
public class UserTag {

    private Long userId;
    /**
     * 0=男，1=女
     */
    private Integer sex;
    /**
     * 名字
     */
    private String name;

    /**
     * 头像ID
     */
    private Integer profilePhotoId;
    /**
     * 标签名
     */
    private List<String> tagNames;

}
