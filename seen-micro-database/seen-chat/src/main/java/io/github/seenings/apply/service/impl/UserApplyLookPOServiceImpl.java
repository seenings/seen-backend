package io.github.seenings.apply.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.apply.po.UserApplyLookPO;
import io.github.seenings.apply.service.UserApplyLookService;
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
interface UserApplyLookPOMapper extends BaseMapper<UserApplyLookPO> {}

@AllArgsConstructor
@Repository
public class UserApplyLookPOServiceImpl
        implements UserApplyLookService {

    private UserApplyLookPOMapper userApplyLookPOMapper;

    @Override
    public Map<Integer, LocalDateTime> applyIdToLookTime(Set<Integer> applyIds) {
        List<Integer> list = CollUtil.valueIsNullToList(applyIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> userApplyLookPOMapper.selectList(new LambdaQueryWrapper<UserApplyLookPO>()
                                .in(UserApplyLookPO::getApplyId, subs)
                                .select(UserApplyLookPO::getApplyId, UserApplyLookPO::getLookTime))
                        .stream())
                .collect(Collectors.toMap(UserApplyLookPO::getApplyId, UserApplyLookPO::getLookTime, (o1, o2) -> o2));
    }

    @Override
    public Integer set(Integer applyId, LocalDateTime lookTime) {

        UserApplyLookPO po =
                new UserApplyLookPO().setApplyId(applyId).setLookTime(lookTime).setCreateTime(LocalDateTime.now());
        userApplyLookPOMapper.insert(po);
        return po.getId();
    }
}
