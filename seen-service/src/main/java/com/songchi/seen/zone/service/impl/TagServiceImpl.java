package com.songchi.seen.zone.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.songchi.seen.info.model.UserInfo;
import com.songchi.seen.info.service.InfoService;
import com.songchi.seen.text.http.HttpTagService;
import com.songchi.seen.zone.model.UserTag;
import com.songchi.seen.zone.service.ITagParentService;
import com.songchi.seen.zone.service.ITagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TagServiceImpl implements ITagService {

    @Resource
    private InfoService infoService;

    @Resource
    private HttpTagService httpTagService;

    @Resource
    private ITagParentService iTagParentService;

    /**
     * 根据父标签名获取标签名
     *
     * @param tagParentNames 父标签名
     * @param userId         登录用户ID
     * @return 父标签名对应标签名
     */
    @Override
    public Map<String, String> tagParentNameToTagName(Set<String> tagParentNames, Long userId) {

        Map<String, Integer> tagParentNameToTagParentIdMap = iTagParentService.tagParentNameToTagParentId(tagParentNames);
        Collection<Integer> tagParentIds = tagParentNameToTagParentIdMap.values();
        List<Integer> tagIds = httpTagService.userIdToTagId(Collections.singleton(userId)).get(userId);
        Map<Integer, Integer> tagIdToTagParentIdMap = httpTagService.tagIdToParentTagId(new HashSet<>(tagIds));
        Set<Integer> filterTagIds = tagIdToTagParentIdMap.entrySet().stream().parallel().filter(n -> tagParentIds.contains(n.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
        Map<Integer, String> tagIdToTagNameMap = httpTagService.tagIdToTagName(filterTagIds);
        return tagParentNames.stream().parallel().collect(Collectors.toMap(Function.identity(), n -> {
            Integer tagParentId = tagParentNameToTagParentIdMap.get(n);
            if (tagParentId != null) {
                return tagIdToTagParentIdMap.entrySet().stream().filter(m -> tagParentId.equals(m.getValue())).map(Map.Entry::getKey).map(tagIdToTagNameMap::get).filter(Objects::nonNull).collect(Collectors.joining(","));
            } else {
                return "";
            }
        }, (o1, o2) -> o2));
    }

    /**
     * 根据用户ID获取用户标签
     *
     * @param userIds 用户ID
     * @return 用户ID对应用户标签
     */
    @Override
    public Map<Long, UserTag> userIdToUserTag(Set<Long> userIds) {
        Map<Long, UserInfo> userIdToInfoMap = infoService.userIdToUserInfo(userIds);
        Map<Long, List<Integer>> userIdToTagIdMap = httpTagService.userIdToTagId(userIds);
        Set<Integer> tagIds = userIdToTagIdMap.values().parallelStream().flatMap(Collection::stream).collect(Collectors.toSet());
        Map<Integer, String> tagIdToTagNameMap = httpTagService.tagIdToTagName(tagIds);
        Map<Long, UserTag> userIdToUserTag = userIdToInfoMap.entrySet().stream().parallel().collect(Collectors.toMap(Map.Entry::getKey, n -> {
            UserInfo info = n.getValue();
            Long userId = n.getKey();
            List<Integer> userIdTagIds = userIdToTagIdMap.get(userId);
            List<String> tagNames;
            if (CollUtil.isEmpty(userIdTagIds)) {
                tagNames = Collections.emptyList();
            } else {
                tagNames = userIdTagIds.stream().parallel().map(tagIdToTagNameMap::get).collect(Collectors.toList());
            }
            return new UserTag().setUserId(userId).setProfilePhotoId(0).setSex(info.sex().getIndex()).setName(info.aliasName()).setTagNames(tagNames);
        }));
        return userIdToUserTag.entrySet().stream().collect(HashMap::new, (map, n) -> {
            if (userIds.contains(n.getKey())) {
                map.put(n.getKey(), n.getValue());
            } else {
                map.put(n.getKey(), new UserTag());
            }
        }, HashMap::putAll);
    }
}
