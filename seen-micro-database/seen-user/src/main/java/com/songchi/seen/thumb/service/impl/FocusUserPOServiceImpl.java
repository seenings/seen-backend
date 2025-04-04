package com.songchi.seen.thumb.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.thumb.po.FocusUserPO;
import com.songchi.seen.thumb.service.FocusUserPOService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FocusUserPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@Mapper
interface FocusUserPOMapper extends BaseMapper<FocusUserPO> {}

@Service
public class FocusUserPOServiceImpl extends ServiceImpl<FocusUserPOMapper, FocusUserPO> implements FocusUserPOService {

    /**
     * 根据被关注者获取是否关注
     * @param focusedUserIds 被关注者
     * @param focusUserId   关注者
     * @return  被关注者对应是否关注
     */
    @Override
    public Map<Long, Boolean> focusedUserIdToTrue(Set<Long> focusedUserIds, Long focusUserId) {
        List<Long> list = CollUtils.valueIsNullToList(focusedUserIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<FocusUserPO>()
                                .in(FocusUserPO::getFocusedUserId, subs)
                                .eq(FocusUserPO::getFocusUserId, focusUserId)
                                .select(FocusUserPO::getFocusedUserId, FocusUserPO::getDeleted))
                        .stream())
                .collect(Collectors.toMap(FocusUserPO::getFocusedUserId, n -> n.getDeleted() == 0, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long focusedUserId, Long focusUserId) {
        Map<Long, Boolean> userIdToValueMap = focusedUserIdToTrue(Collections.singleton(focusedUserId), focusUserId);
        Boolean focus = userIdToValueMap.get(focusedUserId);
        FocusUserPO po = new FocusUserPO()
                .setFocusedUserId(focusedUserId)
                .setFocusUserId(focusUserId)
                .setDeleted(focus == null || !focus ? 0 : 1)
                .setUpdateTime(LocalDateTime.now());
        if (focus == null) {
            return save(po);
        } else {
            boolean update = update(
                    po,
                    new QueryWrapper<FocusUserPO>()
                            .lambda()
                            .eq(FocusUserPO::getFocusedUserId, focusedUserId)
                            .eq(FocusUserPO::getFocusUserId, focusUserId));
            if (update) {
                return !focus;
            } else {
                return focus;
            }
        }
    }
}
