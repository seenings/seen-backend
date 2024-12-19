package com.songchi.seen.apply.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.apply.po.UserApplyLookPO;
import com.songchi.seen.apply.service.UserApplyLookService;
import com.songchi.seen.core.util.CollUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

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

@Service
public class UserApplyLookPOServiceImpl extends ServiceImpl<UserApplyLookPOMapper, UserApplyLookPO>
        implements UserApplyLookService {

    @Override
    public Map<Integer, LocalDateTime> applyIdToLookTime(Set<Integer> applyIds) {
        List<Integer> list = CollUtils.valueIsNullToList(applyIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyLookPO>()
                                .in(UserApplyLookPO::getApplyId, subs)
                                .select(UserApplyLookPO::getApplyId, UserApplyLookPO::getLookTime))
                        .stream())
                .collect(Collectors.toMap(UserApplyLookPO::getApplyId, UserApplyLookPO::getLookTime, (o1, o2) -> o2));
    }

    @Override
    public Integer set(Integer applyId, LocalDateTime lookTime) {

        UserApplyLookPO po =
                new UserApplyLookPO().setApplyId(applyId).setLookTime(lookTime).setCreateTime(LocalDateTime.now());
        save(po);
        return po.getId();
    }
}
