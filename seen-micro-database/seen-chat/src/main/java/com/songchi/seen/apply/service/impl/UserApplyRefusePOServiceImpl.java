package com.songchi.seen.apply.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.apply.po.UserApplyRefusePO;
import com.songchi.seen.apply.service.UserApplyRefuseService;
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
 * UserApplyRefusePOServiceImpl
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@Mapper
interface UserApplyRefusePOMapper extends BaseMapper<UserApplyRefusePO> {
}

@Service
public class UserApplyRefusePOServiceImpl extends ServiceImpl<UserApplyRefusePOMapper, UserApplyRefusePO> implements UserApplyRefuseService {


    @Override
    public Map<Integer, LocalDateTime> applyIdToRefuseTime(Set<Integer> applyIds) {
        List<Integer> list = CollUtils.valueIsNullToList(applyIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyRefusePO>()
                        .in(UserApplyRefusePO::getApplyId, subs)
                        .select(UserApplyRefusePO::getApplyId, UserApplyRefusePO::getCreateTime))
                        .stream())
                .collect(
                        Collectors.toMap(UserApplyRefusePO::getApplyId, UserApplyRefusePO::getCreateTime, (o1, o2) -> o2));
    }

    @Override
    public Integer add(Integer applyId, Integer textId) {
        UserApplyRefusePO po = new UserApplyRefusePO()
                .setApplyId(applyId)
                .setTextId(textId)
                .setCreateTime(LocalDateTime.now());
        save(po);
        return po.getId();
    }

}
