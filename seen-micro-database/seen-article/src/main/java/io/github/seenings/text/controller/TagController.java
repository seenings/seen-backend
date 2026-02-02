package io.github.seenings.text.controller;

import io.github.seenings.tag.service.TagParentService;
import io.github.seenings.text.http.HttpTagService;
import io.github.seenings.text.service.TagService;
import io.github.seenings.text.service.TagUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TagController
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@RestController
@AllArgsConstructor
public class TagController implements HttpTagService {

    private TagService tagService;

    private TagUserService tagUserService;

    @Override
    public Map<Long, List<Integer>> userIdToTagId(  Set<Long> userIds) {

        return tagUserService.userIdToTagId(userIds);
    }

    @Override
    public Map<Integer, Set<Long>> tagIdToUserId(  Set<Integer> tagIds) {
        return tagUserService.tagIdToUserId(tagIds);
    }

    @Override
    public Map<Long, Set<String>> userIdToTagName( Set<Long> userIds) {
        Map<Long, List<Integer>> userIdToTagIdMap = tagUserService.userIdToTagId(userIds);
        Set<Integer> tagIds = userIdToTagIdMap.values().stream()
                .parallel()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Map<Integer, String> tagIdToTagNameMap = tagService.tagIdToTagName(tagIds);
        return userIdToTagIdMap.entrySet().stream()
                .parallel()
                .map(n -> {
                    Set<String> tagNames = n.getValue().stream()
                            .parallel()
                            .map(tagIdToTagNameMap::get)
                            .collect(Collectors.toSet());
                    return Pair.of(n.getKey(), tagNames);
                })
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    /**
     * 根据标签ID对应标签名
     *
     * @param tagIds 标签ID
     * @return 标签ID对应标签名
     */
    @Override
    public Map<Integer, String> tagIdToTagName( Set<Integer> tagIds) {
        return tagService.tagIdToTagName(tagIds);
    }

    private TagParentService tagParentService;

    @Override
    public Map<Integer, String> toParentIdToParentName() {
        return tagParentService.toParentIdToParentName();
    }

    @Override
    public Map<Integer, List<Integer>> toParentIdToTagId() {
        return tagService.toParentIdToTagId();
    }

    @Override
    public Map<Integer, String> toTagIdToTagName() {
        return tagService.toTagIdToTagName();
    }

    @Override
    public Map<Integer, Integer> tagIdToParentTagId(  Set<Integer> tagIds) {
        return tagService.tagIdToTagParentId(tagIds);
    }

    @Override
    public List<Integer> deleteAndSave(  Long userId,   List<Integer> tagIds) {
        return tagUserService.deleteAndSave(userId, tagIds);
    }
}
