package io.github.seenings.text.controller;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.text.http.HttpTextService;
import io.github.seenings.text.service.TextService;
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
