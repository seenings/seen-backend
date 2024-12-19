package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.info.enumeration.Sex;
import com.songchi.seen.info.po.UserSexPO;
import com.songchi.seen.info.service.UserSexService;
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
 * @since 2022-10-06
 */
@Mapper
interface UserSexPOMapper extends BaseMapper<UserSexPO> {}

@Service
public class UserSexPOServiceImpl extends ServiceImpl<UserSexPOMapper, UserSexPO> implements UserSexService {

    /**
     * 根据用户ID获取性别
     *
     * @param userIds 用户ID
     * @return 用户ID对应性别
     */
    @Override
    public Map<Integer, Integer> userIdToSex(Set<Integer> userIds) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserSexPO>()
                                .in(UserSexPO::getUserId, subs)
                                .select(UserSexPO::getUserId, UserSexPO::getSex))
                        .stream())
                .collect(Collectors.toMap(UserSexPO::getUserId, UserSexPO::getSex, (o1, o2) -> o2));
    }

    @Override
    public long sexCount(Sex sex) {
        return count(new QueryWrapper<UserSexPO>().lambda().eq(UserSexPO::getSex, sex.getIndex()));
    }

    @Override
    public List<Integer> sexToUserId(Sex sex, int current, int size) {
        Page<UserSexPO> page = page(
                new Page<>(current, size),
                new QueryWrapper<UserSexPO>()
                        .lambda()
                        .eq(UserSexPO::getSex, sex.getIndex())
                        .select(UserSexPO::getUserId));
        return page.getRecords().stream().parallel().map(UserSexPO::getUserId).collect(Collectors.toList());
    }

    @Override
    public boolean set(Integer userId, Sex sex) {
        Map<Integer, Integer> userIdToSexMap = userIdToSex(Collections.singleton(userId));
        Integer sexId = userIdToSexMap.get(userId);
        UserSexPO po = new UserSexPO().setUserId(userId).setSex(sex.getIndex()).setUpdateTime(LocalDateTime.now());
        if (sexId == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserSexPO>().eq(UserSexPO::getUserId, userId));
        }
    }
}
