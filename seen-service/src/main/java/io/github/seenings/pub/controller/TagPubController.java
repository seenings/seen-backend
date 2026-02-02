package io.github.seenings.pub.controller;

import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.text.http.HttpTagService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * TagController
 *
 * @author chixuehui
 * @since 2023-02-13
 */
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.PUBLIC + "pub/tag")
public class TagPubController {

    private HttpTagService httpTagService;

    @GetMapping("to-parent-id-to-parent-name")
    public R<Map<Integer, String>> toParentIdToParentName() {
        Map<Integer, String> result = httpTagService.toParentIdToParentName();
        return ResUtils.ok(result);
    }

    @GetMapping("to-parent-id-to-tag-id")
    public R<Map<Integer, List<Integer>>> toParentIdToTagId() {
        return ResUtils.ok(httpTagService.toParentIdToTagId());
    }

    @GetMapping("to-tag-id-to-tag-name")
    public R<Map<Integer, String>> toTagIdToTagName() {
        return ResUtils.ok(httpTagService.toTagIdToTagName());
    }
}
