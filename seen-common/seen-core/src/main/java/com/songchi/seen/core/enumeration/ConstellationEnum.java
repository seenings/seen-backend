package com.songchi.seen.core.enumeration;

import lombok.Getter;

/**
 * ConstellationEnum
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@Getter
public enum ConstellationEnum {

    /**
     *白羊
     */
    ARIES(1, "白羊"),
    /**
     *金牛
     */
    TAURUS(2, "金牛"),
    /**
     *双子
     */
    GEMINI(3, "双子"),
    /**
     *巨蟹
     */
    CANCER(4, "巨蟹"),
    /**
     *狮子
     */
    LEO(5, "狮子"),
    /**
     *处女
     */
    VIRGO(6, "处女"),
    /**
     *天秤
     */
    LIBRA(7, "天秤"),
    /**
     *天蝎
     */
    SCORPIO(8, "天蝎"),
    /**
     *射手
     */
    SAGiTTARIUS(9, "射手"),
    /**
     *摩羯
     */
    CAPRICORN(10, "摩羯"),
    /**
     *水瓶
     */
    AQUARIUS(11, "水瓶"),
    /**
     *双鱼
     */
    PISCES(12, "双鱼"),
    ;

    /**
     * 索引
     */
    private final int index;
    /**
     * 名字
     */
    private final String name;

    /**
     * 构造枚举
     * @param index 索引
     * @param name  名字
     */
    ConstellationEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }


}
