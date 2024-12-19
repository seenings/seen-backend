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
    Map<Integer, String> userIdToPhoneNumber(Set<Integer> userIds);

    Map<String, Integer> phoneNumberToUserId(Set<String> phoneNumbers);

    Integer set(String phoneNumber);
}
