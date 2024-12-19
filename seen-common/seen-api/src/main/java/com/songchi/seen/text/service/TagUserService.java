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

    List<Integer> deleteAndSave(Integer userId, List<Integer> tagIds);

    Map<Integer, List<Integer>> userIdToTagId(Set<Integer> userIds);

    Map<Integer, Set<Integer>> tagIdToUserId(Set<Integer> tagIds);
}
