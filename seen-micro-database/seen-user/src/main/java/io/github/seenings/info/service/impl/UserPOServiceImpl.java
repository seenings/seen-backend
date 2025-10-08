package io.github.seenings.info.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.info.po.UserPO;
import io.github.seenings.info.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
interface UserPOMapper extends BaseMapper<UserPO> {
}

@Slf4j
@Service
public class UserPOServiceImpl extends ServiceImpl<UserPOMapper, UserPO> implements UserService {

    @Override
    public Map<Long, String> userIdToPhoneNumber(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
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
    public Map<String, Long> phoneNumberToUserId(Set<String> phoneNumbers) {
        List<String> list = CollUtil.valueIsNullToList(phoneNumbers);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
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
    public Long set(String phoneNumber) {
        Long userId = phoneNumberToUserId(Collections.singleton(phoneNumber)).get(phoneNumber);
        UserPO po = new UserPO().setPhone(phoneNumber).setUpdateTime(LocalDateTime.now());
        if (userId == null) {
            save(po);
            log.info("{}", po);
            return po.getId();
        } else {
            // 已存在该电话号码
            return userId;
        }
    }
}
