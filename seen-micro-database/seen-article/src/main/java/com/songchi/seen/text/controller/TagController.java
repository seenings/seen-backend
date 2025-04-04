package com.songchi.seen.text.controller;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.tag.service.TagParentService;
import com.songchi.seen.text.http.HttpTagService;
import com.songchi.seen.text.service.TagService;
import com.songchi.seen.text.service.TagUserService;
import jakarta.annotation.Resource;
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
@RequestMapping(SeenConstant.FEIGN_VERSION + "text/tag")
public class TagController implements HttpTagService {

    @Resource
    private TagService tagService;

    @Resource
    private TagUserService tagUserService;

    @Override
    @PostMapping("user-id-to-tag-id")
    public Map<Long, List<Integer>> userIdToTagId(@RequestBody Set<Long> userIds) {

        return tagUserService.userIdToTagId(userIds);
    }

    @Override
    @PostMapping("tag-id-to-user-id")
    public Map<Integer, Set<Long>> tagIdToUserId(@RequestBody Set<Integer> tagIds) {
        return tagUserService.tagIdToUserId(tagIds);
    }

    @Override
    @PostMapping("user-id-to-tag-name")
    public Map<Long, Set<String>> userIdToTagName(@RequestBody Set<Long> userIds) {
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
    @PostMapping("tag-id-to-tag-name")
    public Map<Integer, String> tagIdToTagName(@RequestBody Set<Integer> tagIds) {
        return tagService.tagIdToTagName(tagIds);
    }

    @Resource
    private TagParentService tagParentService;

    @Override
    @PostMapping("to-parent-id-to-parent-name")
    public Map<Integer, String> toParentIdToParentName() {
        return tagParentService.toParentIdToParentName();
    }

    @Override
    @PostMapping("to-parent-id-to-tag-id")
    public Map<Integer, List<Integer>> toParentIdToTagId() {
        return tagService.toParentIdToTagId();
    }

    @Override
    @PostMapping("to-tag-id-to-tag-name")
    public Map<Integer, String> toTagIdToTagName() {
        return tagService.toTagIdToTagName();
    }

    @Override
    @PostMapping("tag-id-to-parent-tag-id")
    public Map<Integer, Integer> tagIdToParentTagId(@RequestBody Set<Integer> tagIds) {
        return tagService.tagIdToTagParentId(tagIds);
    }

    @Override
    @PostMapping("delete-and-save")
    public List<Integer> deleteAndSave(@RequestParam("userId") Long userId, @RequestBody List<Integer> tagIds) {
        return tagUserService.deleteAndSave(userId, tagIds);
    }
}
