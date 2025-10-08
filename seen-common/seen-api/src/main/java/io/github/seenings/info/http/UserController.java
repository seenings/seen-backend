package io.github.seenings.info.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * 用户信息
 */
@HttpExchange(FEIGN_VERSION + "user/user")
public interface UserController {
    @PostExchange("user-id-to-phone-number")
    Map<Long, String> userIdToPhoneNumber(@RequestBody Set<Long> userIds);

    @PostExchange("phone-number-to-user-id")
    Map<String, Long> phoneNumberToUserId(@RequestBody Set<String> phoneNumbers);

    @PostExchange("set")
    Long set(@RequestParam("phoneNumber") String phoneNumber);
}
