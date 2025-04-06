package io.github.seenings.info.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * UserInfo
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@Data
@Accessors(chain = true)
public class UserIntroduceInfo {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户认证
     */
    private Integer userAuthId;
    /**
     * 昵称
     */
    private String aliasName;
    /**
     * 现居城市名
     */
    private String currentResidenceCityName;
    /**
     * 出生省份名
     */
    private String birthPlaceProvinceName;
    /**
     * 出生城市名
     */
    private String birthPlaceCityName;
    /**
     * 出生年份
     */
    private String birthYear;
    /**
     * 星座
     */
    private String constellation;
    /**
     * 体重（千克）
     */
    private int weightKg;

    /**
     * 身高（厘米）
     */
    private int statureCm;
    /**
     * 学历
     */
    private Integer educationId;
    /**
     * 就读学校
     */
    private String schoolName;
    /**
     * 职业
     */
    private String workPositionName;
    /**
     * 工作公司
     */
    private String workCompanyName;
}
