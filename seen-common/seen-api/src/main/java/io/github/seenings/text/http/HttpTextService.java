package io.github.seenings.text.http;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * HttpTextService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_ARTICLE,
        path = SeenConstant.FEIGN_VERSION + "text/text",
        contextId = "HttpTextService")
public interface HttpTextService {

    @PostMapping("save-and-return-id")
    Integer saveAndReturnId(@RequestParam("text") String text);

    @PostMapping("text-id-to-text")
    Map<Integer, String> textIdToText(@RequestBody Set<Integer> textIds);
}
