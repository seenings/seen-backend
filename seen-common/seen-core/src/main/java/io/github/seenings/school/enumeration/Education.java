package io.github.seenings.school.enumeration;

import lombok.Getter;

/**
 * Education
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@Getter
public enum Education {
    OTHER(0, "其他"),
    ASSOCIATE(1, "大专"),
    BACHELOR(2, "本科"),
    MASTER(3, "硕士"),
    DOCTOR(4, "博士"),
    ;

    private final int index;
    private final String name;

    Education(int index, String name) {
        this.index = index;
        this.name = name;
    }

}
