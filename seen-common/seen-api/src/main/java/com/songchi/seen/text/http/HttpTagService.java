package com.songchi.seen.text.http;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HttpTagService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_ARTICLE,
        path = SeenConstant.FEIGN_VERSION + "text/tag",
        contextId = "HttpTagService")
public interface HttpTagService {
    @PostMapping("user-id-to-tag-id")
    Map<Integer, List<Integer>> userIdToTagId(@RequestBody Set<Integer> userIds);

    @PostMapping("tag-id-to-user-id")
    Map<Integer, Set<Integer>> tagIdToUserId(@RequestBody Set<Integer> tagIds);

    @PostMapping("user-id-to-tag-name")
    Map<Integer, Set<String>> userIdToTagName(@RequestBody Set<Integer> userIds);

    /**
     * 根据标签ID对应标签名
     *
     * @param tagIds 标签ID
     * @return 标签ID对应标签名
     */
    @PostMapping("tag-id-to-tag-name")
    Map<Integer, String> tagIdToTagName(@RequestBody Set<Integer> tagIds);

    @PostMapping("to-parent-id-to-parent-name")
    Map<Integer, String> toParentIdToParentName();

    @PostMapping("to-parent-id-to-tag-id")
    Map<Integer, List<Integer>> toParentIdToTagId();

    @PostMapping("to-tag-id-to-tag-name")
    Map<Integer, String> toTagIdToTagName();

    @PostMapping("tag-id-to-parent-tag-id")
    Map<Integer, Integer> tagIdToParentTagId(@RequestBody Set<Integer> tagIds);

    @PostMapping("delete-and-save")
    List<Integer> deleteAndSave(@RequestParam("userId") Integer userId, @RequestBody List<Integer> tagIds);
}
