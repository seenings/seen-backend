package io.github.seenings.text.controller;

import io.github.seenings.text.http.HttpTextService;
import io.github.seenings.text.service.TextService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * TextController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@AllArgsConstructor
public class TextController implements HttpTextService {

    private TextService textService;

    @Override
    public Integer saveAndReturnId(String text) {
        return textService.saveAndReturnId(text);
    }

    @Override
    public Map<Integer, String> textIdToText(Set<Integer> textIds) {
        return textService.textIdToText(textIds);
    }
}
