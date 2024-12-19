package com.songchi.seen.chat.controller;


import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.text.http.HttpTextService;
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
