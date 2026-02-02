package io.github.seenings.text.http;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * HttpTextService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@HttpExchange(  SeenConstant.FEIGN_VERSION + "text/text" )
public interface HttpTextService {

    @PostExchange("save-and-return-id")
    Integer saveAndReturnId(@RequestParam("text") String text);

    @PostExchange("text-id-to-text")
    Map<Integer, String> textIdToText(@RequestBody Set<Integer> textIds);
}
