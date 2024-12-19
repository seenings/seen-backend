package com.songchi.seen.text.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TagService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
public interface TagService {
    Integer saveAndReturnId(Integer parentTagId, String tagName);

    Map<Integer, String> tagIdToTagName(Set<Integer> tagIds);

    Map<Integer, Integer> tagIdToTagParentId(Set<Integer> tagIds);

    Map<Integer, List<Integer>> toParentIdToTagId();

    Map<Integer,String> toTagIdToTagName();
}
