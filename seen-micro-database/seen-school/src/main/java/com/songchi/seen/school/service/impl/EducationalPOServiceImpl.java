package com.songchi.seen.school.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.school.enumeration.Education;
import com.songchi.seen.school.mapper.EducationalPOMapper;
import com.songchi.seen.school.po.EducationalPO;
import com.songchi.seen.school.service.EducationalService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * EducationalPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@Service
public class EducationalPOServiceImpl extends ServiceImpl<EducationalPOMapper, EducationalPO>
        implements EducationalService {

    /**
     * 根据用户ID获取学历
     * @param userIds   用户ID
     * @return  用户ID对应学历
     */
    @Override
    public Map<Integer, Integer> userIdToEducational(Set<Integer> userIds) {
        if (userIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(userIds), 100).stream()
                .flatMap(subs -> list(new QueryWrapper<EducationalPO>()
                                .lambda()
                                .in(EducationalPO::getUserId, subs)
                                .select(EducationalPO::getUserId, EducationalPO::getEducational))
                        .stream())
                .collect(Collectors.toMap(EducationalPO::getUserId, EducationalPO::getEducational, (o1, o2) -> o2));
    }

    /**
     * 设置用户的学历
     * @param userId    用户
     * @param education 学历
     * @return  实际写入数据
     */
    @Override
    public boolean set(Integer userId, Education education) {
        Map<Integer, Integer> userIdToEducationalMap = userIdToEducational(Collections.singleton(userId));
        Integer educational = userIdToEducationalMap.get(userId);
        if (educational == null) {
            return save(new EducationalPO()
                    .setUserId(userId)
                    .setEducational(education.getIndex())
                    .setUpdateTime(LocalDateTime.now()));
        } else {
            return update(
                    new EducationalPO()
                            .setUserId(userId)
                            .setEducational(education.getIndex())
                            .setUpdateTime(LocalDateTime.now()),
                    new QueryWrapper<EducationalPO>().lambda().eq(EducationalPO::getUserId, userId));
        }
    }
}
