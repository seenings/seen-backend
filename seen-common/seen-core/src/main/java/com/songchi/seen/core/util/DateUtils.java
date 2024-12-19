package com.songchi.seen.core.util;

import com.songchi.seen.core.enumeration.ConstellationEnum;

/**
 * DateUtils
 *
 * @author chixuehui
 * @since 2022-12-03
 */
public class DateUtils {
    public static ConstellationEnum monthAndDayToConstellationEnum(Integer month, Integer day) {
        if (month != null) {
            switch (month) {
                case 1 -> {
                    if (between(day, 1, 19)) {
                        return ConstellationEnum.CAPRICORN;
                    }
                    if (between(day, 20, 31)) {
                        return ConstellationEnum.AQUARIUS;
                    }
                    return null;
                }
                case 2 -> {
                    if (between(day, 1, 18)) {
                        return ConstellationEnum.AQUARIUS;
                    }
                    if (between(day, 19, 29)) {
                        return ConstellationEnum.PISCES;
                    }
                    return null;
                }
                case 3 -> {
                    if (between(day, 1, 20)) {
                        return ConstellationEnum.PISCES;
                    }
                    if (between(day, 21, 31)) {
                        return ConstellationEnum.ARIES;
                    }
                    return null;
                }
                case 4 -> {
                    if (between(day, 1, 19)) {
                        return ConstellationEnum.ARIES;
                    }
                    if (between(day, 20, 30)) {
                        return ConstellationEnum.TAURUS;
                    }
                    return null;
                }
                case 5 -> {
                    if (between(day, 1, 20)) {
                        return ConstellationEnum.TAURUS;
                    }
                    if (between(day, 21, 31)) {
                        return ConstellationEnum.GEMINI;
                    }
                    return null;
                }
                case 6 -> {
                    if (between(day, 1, 21)) {
                        return ConstellationEnum.GEMINI;
                    }
                    if (between(day, 22, 30)) {
                        return ConstellationEnum.CANCER;
                    }
                    return null;
                }
                case 7 -> {
                    if (between(day, 1, 22)) {
                        return ConstellationEnum.CANCER;
                    }
                    if (between(day, 23, 31)) {
                        return ConstellationEnum.LEO;
                    }
                    return null;
                }
                case 8 -> {
                    if (between(day, 1, 22)) {
                        return ConstellationEnum.LEO;
                    }
                    if (between(day, 23, 31)) {
                        return ConstellationEnum.VIRGO;
                    }
                    return null;
                }
                case 9 -> {
                    if (between(day, 1, 22)) {
                        return ConstellationEnum.VIRGO;
                    }
                    if (between(day, 23, 30)) {
                        return ConstellationEnum.LIBRA;
                    }
                    return null;
                }
                case 10 -> {
                    if (between(day, 1, 23)) {
                        return ConstellationEnum.LIBRA;
                    }
                    if (between(day, 24, 31)) {
                        return ConstellationEnum.SCORPIO;
                    }
                    return null;
                }
                case 11 -> {
                    if (between(day, 1, 22)) {
                        return ConstellationEnum.SCORPIO;
                    }
                    if (between(day, 23, 30)) {
                        return ConstellationEnum.SAGiTTARIUS;
                    }
                    return null;
                }
                case 12 -> {
                    if (between(day, 1, 21)) {
                        return ConstellationEnum.SAGiTTARIUS;
                    }
                    if (between(day, 22, 31)) {
                        return ConstellationEnum.CAPRICORN;
                    }
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 数字在两个数字之间
     *
     * @param value 数字
     * @param min   小的
     * @param max   大的
     * @return 是否
     */
    public static boolean between(int value, int min, int max) {
        return min <= value && value <= max;
    }

    public static String monthAndDayToConstellation(Integer month, Integer day) {
        ConstellationEnum constellationEnum = monthAndDayToConstellationEnum(month, day);
        if (constellationEnum == null) {
            return null;
        }
        return constellationEnumToConstellation(constellationEnum);
    }

    public static String constellationEnumToConstellation(ConstellationEnum constellationEnum) {
        return constellationEnum.getName() + "座";
    }
}
