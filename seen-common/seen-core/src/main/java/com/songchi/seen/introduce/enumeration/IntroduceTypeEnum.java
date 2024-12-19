package com.songchi.seen.introduce.enumeration;

import lombok.Getter;

/**
 * IntroduceTypeEnum
 *
 */
@Getter
public enum IntroduceTypeEnum {
    MAIN_PAGE(0, "主页"),
    PERSON_INTRODUCE(1, "个人简介"),
    FAMILY_INTRODUCE(2, "家庭背景"),
    INTEREST(3, "兴趣喜好"),
    OTHER_HALF(4, "对另一半要求"),
    VIEW_POINT_OF_LOVE(5, "爱情观"),
    YEARNING_FOR_THE_FUTURE(6, "对未来的向往"),
    ; //

    private final Integer index;
    private final String name;

    IntroduceTypeEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
}
