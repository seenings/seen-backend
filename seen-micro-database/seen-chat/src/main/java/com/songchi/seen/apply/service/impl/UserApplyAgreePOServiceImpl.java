package com.songchi.seen.apply.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.apply.po.UserApplyAgreePO;
import com.songchi.seen.apply.service.UserApplyAgreeService;
import com.songchi.seen.core.util.CollUtil;
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
interface UserApplyAgreePOMapper extends BaseMapper<UserApplyAgreePO> {}

@Service
public class UserApplyAgreePOServiceImpl extends ServiceImpl<UserApplyAgreePOMapper, UserApplyAgreePO>
        implements UserApplyAgreeService {

    @Override
    public Map<Integer, LocalDateTime> applyIdToAgreeTime(Set<Integer> applyIds ) {
        List<Integer> list = CollUtil.valueIsNullToList(applyIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyAgreePO>()
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
        save(po);
        return po.getId();
    }
}
