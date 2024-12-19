package com.songchi.seen.introduce.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.sys.util.ListUtils;
import com.songchi.seen.extra.util.ExtraListUtil;
import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.model.IntroduceTypeAndPhotoInfo;
import com.songchi.seen.introduce.model.OrderAndPhotoId;
import com.songchi.seen.introduce.po.UserIntroducePhotoPO;
import com.songchi.seen.introduce.service.UserIntroducePhotoService;
import com.songchi.seen.introduce.service.UserIntroducePhotoToPhotoPOService;
import com.songchi.seen.introduce.util.IntroduceEnumUtils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import lombok.AllArgsConstructor;

/**
 * UserIntroduceServiceImpl
 */
@Mapper
interface UserIntroducePhotoPOMapper extends BaseMapper<UserIntroducePhotoPO> {
}

@Service
@AllArgsConstructor
public class UserIntroducePhotoPOServiceImpl extends ServiceImpl<UserIntroducePhotoPOMapper, UserIntroducePhotoPO>
        implements UserIntroducePhotoService {

    /**
     * 用户介绍照片对应关系
     */
    private UserIntroducePhotoToPhotoPOService userIntroducePhotoToPhotoPOService;

    /**
     * 存入多个照片
     *
     * @param userId           用户ID
     * @param orderAndPhotoIds 介绍照片
     * @return 存入的介绍照片信息ID
     */
    @Override
    public Set<Integer> saveAndReturnId(List<OrderAndPhotoId> orderAndPhotoIds, Integer max,
            IntroduceTypeEnum introduceTypeEnum, Integer userId) {
        Map<Integer, Integer> orderIdToPhotoIdMap = orderAndPhotoIds.stream().map(n -> {
            return Map.entry(n.order(), n.photoId());
        }).collect(Collectors.toMap(n -> n.getKey(), n -> n.getValue()));
        Set<Integer> all = IntStream.range(1, max + 1).boxed().collect(Collectors.toSet());
        return all.stream().map(order -> {
            Integer photoId = orderIdToPhotoIdMap.get(order);
            UserIntroducePhotoPO po = new UserIntroducePhotoPO().setUserId(userId).setOrderNum(order)
                    .setIntroduceType(introduceTypeEnum.getIndex()).setUpdateTime(LocalDateTime.now());
            save(po);
            if (photoId != null) {
                userIntroducePhotoToPhotoPOService.insert(po.getId(), photoId);
            }
            return po.getId();
        }).collect(Collectors.toSet());

    }

    /**
     * 根据ID获取修改时间
     *
     * @param ids ID
     * @return ID对应修改时间
     */
    @Override
    public Map<Integer, LocalDateTime> idToModifyTime(Set<Integer> ids) {
        return ListUtil.partition(ids.stream().toList(), 100).stream().parallel().flatMap(
                subs -> list(new QueryWrapper<UserIntroducePhotoPO>().lambda().in(UserIntroducePhotoPO::getId, subs)
                        .select(UserIntroducePhotoPO::getId, UserIntroducePhotoPO::getUpdateTime)).stream())
                .collect(Collectors.toMap(UserIntroducePhotoPO::getId, UserIntroducePhotoPO::getUpdateTime));
    }

    /**
     * 根据ID获取顺序
     *
     * @param ids ID
     * @return ID对应顺序
     */
    @Override
    public Map<Integer, Integer> idToOrder(Set<Integer> ids) {
        return ListUtil.partition(ids.stream().toList(), 100).stream().parallel().flatMap(
                subs -> list(new QueryWrapper<UserIntroducePhotoPO>().lambda().in(UserIntroducePhotoPO::getId, subs)
                        .select(UserIntroducePhotoPO::getId, UserIntroducePhotoPO::getOrderNum)).stream())
                .collect(Collectors.toMap(UserIntroducePhotoPO::getId, UserIntroducePhotoPO::getOrderNum));
    }

    /**
     * 根据介绍类型ID获取照片介绍信息ID
     *
     * @param introduceTypeIds 介绍类型ID
     * @param userId           用户ID
     * @return 介绍类型ID对应照片介绍信息ID
     */
    @Override
    public Map<Integer, Set<Integer>> introduceTypeIdToId(Set<Integer> introduceTypeIds, Integer userId) {
        List<Integer> list = ListUtils.valueIsNull(introduceTypeIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream().parallel()
                .flatMap(subs -> list(new QueryWrapper<UserIntroducePhotoPO>().lambda()
                        .eq(UserIntroducePhotoPO::getUserId, userId).in(UserIntroducePhotoPO::getIntroduceType, subs)
                        .select(UserIntroducePhotoPO::getIntroduceType, UserIntroducePhotoPO::getId)).stream())
                .collect(Collectors.groupingBy(UserIntroducePhotoPO::getIntroduceType,
                        Collectors.mapping(UserIntroducePhotoPO::getId, Collectors.toSet())));
    }

    /**
     * 根据用户ID获取介绍照片信息
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍照片信息
     */
    @Override
    public Map<Integer, Set<IntroduceTypeAndPhotoInfo>> userIdToIntroduceTypeAndPhoto(Set<Integer> userIds) {

        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        return ExtraListUtil.partition(userIds).stream().parallel().flatMap(
                subs -> list(new QueryWrapper<UserIntroducePhotoPO>().lambda().in(UserIntroducePhotoPO::getUserId, subs)
                        .select(UserIntroducePhotoPO::getUserId, UserIntroducePhotoPO::getId,
                                UserIntroducePhotoPO::getIntroduceType, UserIntroducePhotoPO::getOrderNum))
                        .stream())
                .collect(Collectors.groupingBy(UserIntroducePhotoPO::getUserId,
                        Collectors.mapping(n -> new IntroduceTypeAndPhotoInfo(
                                IntroduceEnumUtils.indexToIntroduceTypeEnum(n.getIntroduceType()), n.getOrderNum(),
                                n.getId()), Collectors.toSet())));
    }
}
