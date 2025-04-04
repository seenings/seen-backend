package com.songchi.seen.text.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TagUserService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
public interface TagUserService {

    List<Integer> deleteAndSave(Long userId, List<Integer> tagIds);

    Map<Long, List<Integer>> userIdToTagId(Set<Long> userIds);

    Map<Integer, Set<Long>> tagIdToUserId(Set<Integer> tagIds);
}
