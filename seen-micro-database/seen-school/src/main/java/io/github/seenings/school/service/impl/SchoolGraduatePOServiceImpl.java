package io.github.seenings.school.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.school.po.SchoolGraduatePO;
import io.github.seenings.school.service.SchoolGraduateService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SchoolGraduatePOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@Mapper
interface SchoolGraduatePOMapper extends BaseMapper<SchoolGraduatePO> {}

@Service
public class SchoolGraduatePOServiceImpl extends ServiceImpl<SchoolGraduatePOMapper, SchoolGraduatePO>
        implements SchoolGraduateService {

    @Override
    public Map<Long, Integer> userIdToGraduated(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<SchoolGraduatePO>()
                                .in(SchoolGraduatePO::getUserId, subs)
                                .select(SchoolGraduatePO::getUserId, SchoolGraduatePO::getGraduated))
                        .stream())
                .collect(Collectors.toMap(SchoolGraduatePO::getUserId, SchoolGraduatePO::getGraduated, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer graduated) {
        Map<Long, Integer> userIdToGraduatedMap = userIdToGraduated(Collections.singleton(userId));
        Integer exists = userIdToGraduatedMap.get(userId);
        SchoolGraduatePO po =
                new SchoolGraduatePO().setUserId(userId).setGraduated(graduated).setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<SchoolGraduatePO>().eq(SchoolGraduatePO::getUserId, userId));
        }
    }
}
