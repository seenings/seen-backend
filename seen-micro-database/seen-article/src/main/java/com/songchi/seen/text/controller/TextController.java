package com.songchi.seen.text.controller;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.text.http.HttpTextService;
import com.songchi.seen.text.service.TextService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * TextController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "text/text")
public class TextController implements HttpTextService {

    @Resource
    private TextService textService;

    @Override
    @PostMapping("save-and-return-id")
    public Integer saveAndReturnId(@RequestParam("text") String text) {
        return textService.saveAndReturnId(text);
    }

    @Override
    @PostMapping("text-id-to-text")
    public Map<Integer, String> textIdToText(@RequestBody Set<Integer> textIds) {
        return textService.textIdToText(textIds);
    }
}
