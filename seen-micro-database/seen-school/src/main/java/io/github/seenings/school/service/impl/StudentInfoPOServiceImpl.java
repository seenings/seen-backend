package io.github.seenings.school.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.school.po.StudentInfoPO;
import io.github.seenings.school.service.StudentInfoService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

@AllArgsConstructor
@Repository
public class StudentInfoPOServiceImpl
        implements StudentInfoService {

    private StudentInfoPOMapper studentInfoPOMapper;

    @Override
    public Map<Long, Integer> userIdToSchoolId(Set<Long> userIds) {

        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> studentInfoPOMapper.selectList(new LambdaQueryWrapper<StudentInfoPO>()
                                .in(StudentInfoPO::getUserId, subs)
                                .select(StudentInfoPO::getUserId, StudentInfoPO::getSchoolId))
                        .stream())
                .collect(Collectors.toMap(StudentInfoPO::getUserId, StudentInfoPO::getSchoolId));
    }

    @Override
    public boolean set(Long userId, Integer schoolId) {
        Map<Long, Integer> userIdToSchoolIdMap = userIdToSchoolId(Collections.singleton(userId));
        Integer exists = userIdToSchoolIdMap.get(userId);
        StudentInfoPO po = new StudentInfoPO()
                .setUserId(userId)
                .setSchoolId(schoolId)
                .setUpdateTime(LocalDateTime.now())
                .setUpdateUser(userId);
        if (exists == null) {
            po.setCreateTime(LocalDateTime.now());
            return studentInfoPOMapper.insert(po)>0;
        } else {
            return studentInfoPOMapper.update(
                    po,
                    new LambdaQueryWrapper<StudentInfoPO>()
                            .eq(StudentInfoPO::getUserId, userId)
                            .eq(StudentInfoPO::getSchoolId, exists))>0;
        }
    }
}
