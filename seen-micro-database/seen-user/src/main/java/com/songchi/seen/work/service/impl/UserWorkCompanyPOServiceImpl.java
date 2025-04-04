package com.songchi.seen.work.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtil;
import com.songchi.seen.work.po.UserWorkCompanyPO;
import com.songchi.seen.work.service.UserWorkCompanyService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserWorkCompanyPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@Mapper
interface UserWorkCompanyPOMapper extends BaseMapper<UserWorkCompanyPO> {}

@Service
public class UserWorkCompanyPOServiceImpl extends ServiceImpl<UserWorkCompanyPOMapper, UserWorkCompanyPO>
        implements UserWorkCompanyService {
    @Override
    public Map<Long, String> userIdToCompanyName(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserWorkCompanyPO, Long> getKey = UserWorkCompanyPO::getUserId;
        SFunction<UserWorkCompanyPO, String> getValue = UserWorkCompanyPO::getCompanyName;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserWorkCompanyPO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, String companyName) {
        Map<Long, String> userIdToValueMap = userIdToCompanyName(Collections.singleton(userId));
        String exists = userIdToValueMap.get(userId);
        UserWorkCompanyPO po = new UserWorkCompanyPO()
                .setUserId(userId)
                .setCompanyName(companyName)
                .setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserWorkCompanyPO>().eq(UserWorkCompanyPO::getUserId, userId));
        }
    }
}
