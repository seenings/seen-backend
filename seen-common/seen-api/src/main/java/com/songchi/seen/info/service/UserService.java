package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserService
 *
 * @author chixuehui
 * @since 2023-02-11
 */
public interface UserService {
    Map<Long, String> userIdToPhoneNumber(Set<Long> userIds);

    Map<String, Long> phoneNumberToUserId(Set<String> phoneNumbers);

    Long set(String phoneNumber);
}
