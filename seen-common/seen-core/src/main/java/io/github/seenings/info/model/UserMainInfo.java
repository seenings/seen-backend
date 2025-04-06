package io.github.seenings.info.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * UserMainInfo
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Data
@Accessors(chain = true)
public class UserMainInfo {
    private Long userId;
    private Integer mainPhotoId;
    /**
     * 照片url
     */
    private String mainPhotoUrl;

    private Integer userAuthId;
    private String aliasName;
    private String currentResidenceProvinceName;
    private String currentResidenceCityName;
    private String birthPlaceProvinceName;
    private String birthPlaceCityName;
    private Integer birthYear;
    /**
     * 体重（千克）
     */
    private Integer weightKg;
    /**
     * 身高（厘米）
     */
    private Integer statureCm;
    /**
     * 学历
     */
    private Integer educationId;
    /**
     * 职业
     */
    private String workPositionName;
}
