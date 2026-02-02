package io.github.seenings.text.http;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HttpTagService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@HttpExchange(SeenConstant.FEIGN_VERSION + "text/tag")
public interface HttpTagService {
    @PostExchange("user-id-to-tag-id")
    Map<Long, List<Integer>> userIdToTagId(@RequestBody Set<Long> userIds);

    @PostExchange("tag-id-to-user-id")
    Map<Integer, Set<Long>> tagIdToUserId(@RequestBody Set<Integer> tagIds);

    @PostExchange("user-id-to-tag-name")
    Map<Long, Set<String>> userIdToTagName(@RequestBody Set<Long> userIds);

    /**
     * 根据标签ID对应标签名
     *
     * @param tagIds 标签ID
     * @return 标签ID对应标签名
     */
    @PostExchange("tag-id-to-tag-name")
    Map<Integer, String> tagIdToTagName(@RequestBody Set<Integer> tagIds);

    @PostExchange("to-parent-id-to-parent-name")
    Map<Integer, String> toParentIdToParentName();

    @PostExchange("to-parent-id-to-tag-id")
    Map<Integer, List<Integer>> toParentIdToTagId();

    @PostExchange("to-tag-id-to-tag-name")
    Map<Integer, String> toTagIdToTagName();

    @PostExchange("tag-id-to-parent-tag-id")
    Map<Integer, Integer> tagIdToParentTagId(@RequestBody Set<Integer> tagIds);

    @PostExchange("delete-and-save")
    List<Integer> deleteAndSave(@RequestParam("userId") Long userId, @RequestBody List<Integer> tagIds);
}
