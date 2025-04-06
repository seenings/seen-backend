package io.github.seenings.chat.controller;


import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.text.http.HttpTextService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@RestController
@RequestMapping(PublicConstant.REST + "chat/history")
public class HistoryController {
    @Resource
    private HttpTextService httpTextService;

    @PostMapping("text-id-to-content")
    public R<Map<Integer, String>> textIdToContent(@RequestBody Set<Integer> textIds) {
        return ResUtils.ok(httpTextService.textIdToText(textIds));
    }
}
