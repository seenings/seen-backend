package io.github.seenings.introduce.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.extra.util.ExtraListUtil;
import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.model.IntroduceTypeAndText;
import io.github.seenings.introduce.po.UserIntroducePO;
import io.github.seenings.introduce.service.UserIntroduceService;
import io.github.seenings.introduce.util.IntroduceEnumUtils;

import cn.hutool.core.collection.CollUtil;

/**
 * UserIntroduceServiceImpl
 *
 * @author chixuehui
 * @since 2022-11-27
 */
@Mapper
interface UserIntroducePOMapper extends BaseMapper<UserIntroducePO> {
}

@Service
public class UserIntroducePOServiceImpl extends ServiceImpl<UserIntroducePOMapper, UserIntroducePO>
        implements UserIntroduceService {

    /**
     * 保存用户的介绍
     *
     * @param userId            用户ID
     * @param introduceTypeEnum 介绍类型
     * @param textId            文本ID
     *
     * @return 用户介绍ID
     */
    @Override
    public Integer saveAndReturnId(Long userId, IntroduceTypeEnum introduceTypeEnum, Integer textId) {
        LocalDateTime now = LocalDateTime.now();
        UserIntroducePO po = new UserIntroducePO()
                .setUserId(userId)
                .setIntroduceType(introduceTypeEnum.getIndex())
                .setTextId(textId)
                .setCreateTime(now)
                .setUpdateTime(now);
        save(po);
        return po.getId();
    }

    /**
     * 保存用户的介绍
     *
     * @param userId            用户ID
     * @param introduceTypeEnum 介绍类型
     * @param textId            文本ID
     *
     * @return 用户介绍ID
     */
    @Override
    public Integer updateAndReturnId(Long userId, IntroduceTypeEnum introduceTypeEnum, Integer textId) {
        LocalDateTime now = LocalDateTime.now();
        UserIntroducePO po = new UserIntroducePO()
                .setUserId(userId)
                .setIntroduceType(introduceTypeEnum.getIndex())
                .setTextId(textId)
                .setUpdateTime(now);
        update(
                po,
                new QueryWrapper<UserIntroducePO>()
                        .lambda()
                        .eq(UserIntroducePO::getUserId, userId)
                        .eq(UserIntroducePO::getIntroduceType, introduceTypeEnum.getIndex()));
        return po.getId();
    }

    /**
     * 根据用户ID获取介绍文本
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍文本
     */
    @Override
    public Map<Long, Set<IntroduceTypeAndText>> userIdToIntroduceTypeAndText(Set<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        Map<Long, Map<Integer, Set<UserIntroducePO>>> map = ExtraListUtil.partition(userIds).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<UserIntroducePO>().lambda().in(UserIntroducePO::getUserId, subs)
                        .select(UserIntroducePO::getUserId, UserIntroducePO::getIntroduceType,
                                UserIntroducePO::getUpdateTime,
                                UserIntroducePO::getTextId))
                        .stream())
                .collect(Collectors.groupingBy(
                        UserIntroducePO::getUserId,
                        Collectors.groupingBy(UserIntroducePO::getIntroduceType,
                                Collectors.mapping(
                                        Function.identity(),
                                        Collectors.toSet()))));
        return map.entrySet().stream()
                .parallel()
                .map(n -> {
                    Long userId = n.getKey();
                    Set<IntroduceTypeAndText> introduceTypeAndTexts = n.getValue().entrySet().stream().parallel()
                            .map(l -> {
                                Integer introduceType = l.getKey();
                                // 最新时间的文本
                                Integer textId = l.getValue().stream()
                                        .max(Comparator.comparing(UserIntroducePO::getUpdateTime))
                                        .map(UserIntroducePO::getTextId).orElse(null);
                                return new IntroduceTypeAndText(
                                        IntroduceEnumUtils.indexToIntroduceTypeEnum(introduceType), textId);

                            }).collect(Collectors.toSet());
                    return Map.entry(userId, introduceTypeAndTexts);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}
