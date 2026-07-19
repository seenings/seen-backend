package io.github.seenings.apply.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.apply.po.UserApplyAgreePO;
import io.github.seenings.apply.service.UserApplyAgreeService;
import io.github.seenings.core.util.CollUtil;
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
 * UserApplyLookPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Mapper
interface UserApplyAgreePOMapper extends BaseMapper<UserApplyAgreePO> {}

@AllArgsConstructor
@Repository
public class UserApplyAgreePOServiceImpl
        implements UserApplyAgreeService {

    private UserApplyAgreePOMapper userApplyAgreePOMapper;

    @Override
    public Map<Integer, LocalDateTime> applyIdToAgreeTime(Set<Integer> applyIds ) {
        List<Integer> list = CollUtil.valueIsNullToList(applyIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> userApplyAgreePOMapper.selectList(new LambdaQueryWrapper<UserApplyAgreePO>()
                                .in(UserApplyAgreePO::getApplyId, subs)
                                .select(UserApplyAgreePO::getApplyId, UserApplyAgreePO::getAgreeTime))
                        .stream())
                .collect(
                        Collectors.toMap(UserApplyAgreePO::getApplyId, UserApplyAgreePO::getAgreeTime, (o1, o2) -> o2));
    }

    @Override
    public Integer set(Integer applyId, LocalDateTime agreeTime) {
        UserApplyAgreePO po = new UserApplyAgreePO()
                .setApplyId(applyId)
                .setAgreeTime(agreeTime)
                .setCreateTime(LocalDateTime.now());
        userApplyAgreePOMapper.insert(po);
        return po.getId();
    }
}
