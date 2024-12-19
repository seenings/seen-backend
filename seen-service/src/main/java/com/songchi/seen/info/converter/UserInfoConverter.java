package com.songchi.seen.info.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.songchi.seen.info.model.UserMainInfo;

/**
 * UserInfoConverter
 */
@Mapper
public interface UserInfoConverter {
    UserInfoConverter INSTANCE = Mappers.getMapper(UserInfoConverter.class);

    UserMainInfo convert(
            Integer userId,
            Integer mainPhotoId,
            String mainPhotoUrl,
            Integer userAuthId,
            String aliasName,
            String currentResidenceProvinceName,
            String currentResidenceCityName,
            String birthPlaceProvinceName,
            String birthPlaceCityName,
            Integer birthYear,
            /*
             * 体重（千克）
             */
            Integer weightKg,
            /*
             * 身高（厘米）
             */
            Integer statureCm,
            /*
             * 学历
             */
            Integer educationId,
            /*
             * 职业
             */
            String workPositionName);
}
