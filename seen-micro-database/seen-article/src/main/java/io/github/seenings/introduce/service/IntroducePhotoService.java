package io.github.seenings.introduce.service;

import io.github.seenings.introduce.model.IntroduceTypeAndPhoto;

import java.util.Map;
import java.util.Set;

/**
 * IntroducePhotoService
 *
 * @author chixuehui
 * @since 2024-01-07
 */
public interface IntroducePhotoService {
    /**
     * 根据用户ID获取介绍信息对应关系
     * @param userIds   用户ID
     * @return  用户ID对应介绍信息对应关系
     */
    Map<Long, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhoto(Set<Long> userIds);
}
