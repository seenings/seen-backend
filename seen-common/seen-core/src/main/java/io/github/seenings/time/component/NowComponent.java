package io.github.seenings.time.component;

/**
 * 当前时间组件
 */
public interface NowComponent {
    /**
     * 今日转换为ISO - 8601日期格式
     *
     * @return yyyyMMdd格式(ISO - 8601)
     */
    String nowToBasicIsoDate();

    /**
     * 现在转十七位日期格式
     *
     * @return yyyyMMddHHmmssSSS格式
     */
    String nowToSeventeenFormatDate();
}
