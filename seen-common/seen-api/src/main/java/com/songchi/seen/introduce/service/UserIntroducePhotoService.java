package com.songchi.seen.introduce.service;

import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.model.IntroduceTypeAndPhotoInfo;
import com.songchi.seen.introduce.model.OrderAndPhotoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserIntroducePhotoService
 *
 * @author chixuehui
 * @since 2022-11-27
 */
public interface UserIntroducePhotoService {

    /**
     * 存入多个照片
     *
     * @param userId                 用户ID
     * @param introduceTypeAndPhotos 介绍照片
     * @return 存入的介绍照片信息ID
     */
    Set<Integer> saveAndReturnId(List<OrderAndPhotoId> introduceTypeAndPhotos, Integer max, IntroduceTypeEnum introduceTypeEnum, Long userId);

    /**
     * 根据ID获取修改时间
     *
     * @param ids ID
     * @return ID对应修改时间
     */
    Map<Integer, LocalDateTime> idToModifyTime(Set<Integer> ids);

    /**
     * 根据ID获取顺序
     *
     * @param ids ID
     * @return ID对应顺序
     */
    Map<Integer, Integer> idToOrder(Set<Integer> ids);

    /**
     * 根据介绍类型ID获取照片介绍信息ID
     *
     * @param introduceTypeIds 介绍类型ID
     * @param userId           用户ID
     * @return 介绍类型ID对应照片介绍信息ID
     */
    Map<Integer, Set<Integer>> introduceTypeIdToId(Set<Integer> introduceTypeIds, Long userId);

    /**
     * 根据用户ID获取介绍照片信息
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍照片信息
     */
    Map<Long, Set<IntroduceTypeAndPhotoInfo>> userIdToIntroduceTypeAndPhoto(Set<Long> userIds);
}
