package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.info.po.UserPO;
import com.songchi.seen.info.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@Mapper
interface UserPOMapper extends BaseMapper<UserPO> {}

@Service
public class UserPOServiceImpl extends ServiceImpl<UserPOMapper, UserPO> implements UserService {

    @Override
    public Map<Integer, String> userIdToPhoneNumber(Set<Integer> userIds) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserPO>()
                                .in(UserPO::getId, subs)
                                .select(UserPO::getId, UserPO::getPhone))
                        .stream())
                .collect(Collectors.toMap(UserPO::getId, UserPO::getPhone));
    }

    @Override
    public Map<String, Integer> phoneNumberToUserId(Set<String> phoneNumbers) {
        List<String> list = CollUtils.valueIsNullToList(phoneNumbers);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserPO>()
                                .in(UserPO::getPhone, subs)
                                .select(UserPO::getId, UserPO::getPhone))
                        .stream())
                .collect(Collectors.toMap(UserPO::getPhone, UserPO::getId));
    }

    @Override
    public Integer set(String phoneNumber) {
        Integer userId = phoneNumberToUserId(Collections.singleton(phoneNumber)).get(phoneNumber);
        UserPO po = new UserPO().setPhone(phoneNumber).setUpdateTime(LocalDateTime.now());
        if (userId == null) {
            save(po);
            return po.getId();
        } else {
            // 已存在该电话号码
            return userId;
        }
    }
}
