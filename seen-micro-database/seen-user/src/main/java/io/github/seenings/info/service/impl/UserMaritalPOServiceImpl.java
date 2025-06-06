package io.github.seenings.info.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.info.enumeration.MaritalStatus;
import io.github.seenings.info.po.UserMaritalPO;
import io.github.seenings.info.service.UserMaritalService;
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
    public Map<Long, Integer> userIdToMaritalStatus(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
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
    public List<Long> maritalStatusToUserId(MaritalStatus maritalStatus, int current, int size) {
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
    public boolean set(Long userId, MaritalStatus maritalStatus) {
        Map<Long, Integer> userIdToMaritalStatusMap = userIdToMaritalStatus(Collections.singleton(userId));
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
