package com.songchi.seen.thumb.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtil;
import com.songchi.seen.thumb.po.ThumbUserPO;
import com.songchi.seen.thumb.service.ThumbUserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ThumbUserPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@Mapper
interface ThumbUserPOMapper extends BaseMapper<ThumbUserPO> {}

@Service
public class ThumbUserPOServiceImpl extends ServiceImpl<ThumbUserPOMapper, ThumbUserPO> implements ThumbUserService {

    /**
     * 根据被点赞者获取是否点赞
     * @param thumbedUserId 被点赞者
     * @param thumbUserId   点赞者
     * @return  被点赞者对应是否点赞
     */
    @Override
    public Map<Long, Boolean> thumbedUserIdToTrue(Set<Long> thumbedUserId, Long thumbUserId) {
        List<Long> list = CollUtil.valueIsNullToList(thumbedUserId);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<ThumbUserPO>()
                                .in(ThumbUserPO::getThumbedUserId, subs)
                                .eq(ThumbUserPO::getThumbUserId, thumbUserId)
                                .select(ThumbUserPO::getThumbedUserId, ThumbUserPO::getDeleted))
                        .stream())
                .collect(Collectors.toMap(ThumbUserPO::getThumbedUserId, n -> n.getDeleted() == 0, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long thumbedUserId, Long thumbUserId) {
        Map<Long, Boolean> userIdToValueMap = thumbedUserIdToTrue(Collections.singleton(thumbedUserId), thumbUserId);
        Boolean thumb = userIdToValueMap.get(thumbedUserId);
        ThumbUserPO po = new ThumbUserPO()
                .setThumbedUserId(thumbedUserId)
                .setThumbUserId(thumbUserId)
                .setDeleted(thumb == null || !thumb ? 0 : 1)
                .setUpdateTime(LocalDateTime.now());
        if (thumb == null) {
            return save(po);
        } else {
            boolean update = update(
                    po,
                    new QueryWrapper<ThumbUserPO>()
                            .lambda()
                            .eq(ThumbUserPO::getThumbedUserId, thumbedUserId)
                            .eq(ThumbUserPO::getThumbUserId, thumbUserId));
            if (update) {
                return !thumb;
            } else {
                return thumb;
            }
        }
    }
}
