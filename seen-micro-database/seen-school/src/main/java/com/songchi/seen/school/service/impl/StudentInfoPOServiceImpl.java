package com.songchi.seen.school.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.school.po.StudentInfoPO;
import com.songchi.seen.school.service.StudentInfoService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * StudentInfoPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@Mapper
interface StudentInfoPOMapper extends BaseMapper<StudentInfoPO> {}

@Service
public class StudentInfoPOServiceImpl extends ServiceImpl<StudentInfoPOMapper, StudentInfoPO>
        implements StudentInfoService {

    @Override
    public Map<Integer, Integer> userIdToSchoolId(Set<Integer> userIds) {

        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<StudentInfoPO>()
                                .in(StudentInfoPO::getUserId, subs)
                                .select(StudentInfoPO::getUserId, StudentInfoPO::getSchoolId))
                        .stream())
                .collect(Collectors.toMap(StudentInfoPO::getUserId, StudentInfoPO::getSchoolId));
    }

    @Override
    public boolean set(Integer userId, Integer schoolId) {
        Map<Integer, Integer> userIdToSchoolIdMap = userIdToSchoolId(Collections.singleton(userId));
        Integer exists = userIdToSchoolIdMap.get(userId);
        StudentInfoPO po = new StudentInfoPO()
                .setUserId(userId)
                .setSchoolId(schoolId)
                .setUpdateTime(LocalDateTime.now())
                .setUpdateUser(userId);
        if (exists == null) {
            po.setCreateTime(LocalDateTime.now());
            return save(po);
        } else {
            return update(
                    po,
                    new LambdaQueryWrapper<StudentInfoPO>()
                            .eq(StudentInfoPO::getUserId, userId)
                            .eq(StudentInfoPO::getSchoolId, exists));
        }
    }
}
