package com.songchi.seen.photo.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.photo.po.UserMainPhotoPO;
import com.songchi.seen.info.service.UserMainPhotoService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserMainPhotoPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface UserMainPhotoPOMapper extends BaseMapper<UserMainPhotoPO> {}

@Service
public class UserMainPhotoPOServiceImpl extends ServiceImpl<UserMainPhotoPOMapper, UserMainPhotoPO>
        implements UserMainPhotoService {
    @Override
    public Map<Long, Integer> userIdPhotoId(Set<Long> userIds) {
        List<Long> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserMainPhotoPO, Integer> getValue = UserMainPhotoPO::getPhotoId;
        SFunction<UserMainPhotoPO, Long> getKey = UserMainPhotoPO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserMainPhotoPO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer photoId) {
        Integer exists = userIdPhotoId(Collections.singleton(userId)).get(userId);
        var po = new UserMainPhotoPO().setUserId(userId).setPhotoId(photoId).setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserMainPhotoPO>().eq(UserMainPhotoPO::getUserId, userId));
        }
    }
}
