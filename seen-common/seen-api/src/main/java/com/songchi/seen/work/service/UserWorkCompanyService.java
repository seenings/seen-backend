package com.songchi.seen.work.service;

import java.util.Map;
import java.util.Set;

/**
 * UserWorkCompanyService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
public interface UserWorkCompanyService {
    Map<Integer, String> userIdToCompanyName(Set<Integer> userIds);

    boolean set(Integer userId, String companyName);
}
