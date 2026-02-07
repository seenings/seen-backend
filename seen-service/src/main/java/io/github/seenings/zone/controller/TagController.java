package io.github.seenings.zone.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.github.seenings.common.model.R;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.zone.model.UserTag;

import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/// 标签
@HttpExchange(PublicConstant.REST + "zone/tag")
public interface TagController {

    @PostExchange("user-id-to-recommend-user-id")
    R<List<Long>> userIdToRecommendUserId(@SessionAttribute Long userId);

    @PostExchange("tag")
    R<Map<Long, UserTag>> hotTag(@RequestBody Set<Long> userIds);

    /**
     * 根据父标签名获取标签名
     *
     * @param tagParentNames 父标签名
     * @return 父标签名对应标签名
     */
    @PostExchange("tag-parent-name-to-tag-name")
    R<Map<String, String>> tagParentNameToTagName(
            @RequestBody Set<String> tagParentNames, @RequestParam Long otherUserId);
}
