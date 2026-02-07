package io.github.seenings.zone.controller.impl;

import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.zone.controller.TagController;
import io.github.seenings.zone.model.UserTag;
import io.github.seenings.zone.service.ITagService;
import io.github.seenings.zone.service.ResultRecommendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
public class TagControllerImpl implements TagController {

    private ITagService tagService;

    private ResultRecommendService resultRecommendService;

    public R<List<Long>> userIdToRecommendUserId(Long userId) {
        List<Long> recommendUserIds = resultRecommendService.userIdToRecommendUserId(userId);
        return ResUtils.ok(recommendUserIds);
    }

    public R<Map<Long, UserTag>> hotTag(Set<Long> userIds) {
        Map<Long, UserTag> userIdToUserTag = tagService.userIdToUserTag(userIds);
        return ResUtils.ok(userIdToUserTag);
    }

    /**
     * 根据父标签名获取标签名
     *
     * @param tagParentNames 父标签名
     * @return 父标签名对应标签名
     */
    public R<Map<String, String>> tagParentNameToTagName(Set<String> tagParentNames, Long otherUserId) {
        Map<String, String> tagParentNameToTagName = tagService.tagParentNameToTagName(tagParentNames, otherUserId);
        return ResUtils.ok(tagParentNameToTagName);
    }
}
