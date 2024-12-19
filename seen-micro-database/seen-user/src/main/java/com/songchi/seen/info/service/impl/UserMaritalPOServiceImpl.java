package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.info.enumeration.MaritalStatus;
import com.songchi.seen.info.po.UserMaritalPO;
import com.songchi.seen.info.service.UserMaritalService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserSexPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@Mapper
interface UserMaritalPOMapper extends BaseMapper<UserMaritalPO> {}

@Service
public class UserMaritalPOServiceImpl extends ServiceImpl<UserMaritalPOMapper, UserMaritalPO>
        implements UserMaritalService {

    /**
     * 根据用户ID获取婚姻状况
     *
     * @param userIds 用户ID
     * @return 用户ID对应婚姻状况
     */
    @Override
    public Map<Integer, Integer> userIdToMaritalStatus(Set<Integer> userIds) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserMaritalPO>()
                                .in(UserMaritalPO::getUserId, subs)
                                .select(UserMaritalPO::getUserId, UserMaritalPO::getMaritalStatus))
                        .stream())
                .collect(Collectors.toMap(UserMaritalPO::getUserId, UserMaritalPO::getMaritalStatus));
    }

    @Override
    public List<Integer> maritalStatusToUserId(MaritalStatus maritalStatus, int current, int size) {
        Page<UserMaritalPO> page = page(
                new Page<>(current, size),
                new QueryWrapper<UserMaritalPO>()
                        .lambda()
                        .eq(UserMaritalPO::getMaritalStatus, maritalStatus.getIndex())
                        .select(UserMaritalPO::getUserId));
        return page.getRecords().stream()
                .parallel()
                .map(UserMaritalPO::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean set(Integer userId, MaritalStatus maritalStatus) {
        Map<Integer, Integer> userIdToMaritalStatusMap = userIdToMaritalStatus(Collections.singleton(userId));
        Integer maritalStatusId = userIdToMaritalStatusMap.get(userId);
        UserMaritalPO po = new UserMaritalPO()
                .setUserId(userId)
                .setMaritalStatus(maritalStatus.getIndex())
                .setUpdateTime(LocalDateTime.now());
        if (maritalStatusId == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserMaritalPO>().eq(UserMaritalPO::getUserId, userId));
        }
    }
}
