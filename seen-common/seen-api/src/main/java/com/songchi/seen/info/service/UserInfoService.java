package com.songchi.seen.info.service;

import com.songchi.seen.info.model.BasicInformation;
import com.songchi.seen.info.model.ContactInformation;
import com.songchi.seen.info.model.EducationAndWork;
import com.songchi.seen.info.model.UserMainInfo;

import java.util.Map;
import java.util.Set;

/**
 * UserInfoService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserInfoService {
    Map<Long, UserMainInfo> userIdToUserMainInfo(Set<Long> userIds);

    void saveBasicInformation(Long userId, BasicInformation basicInformation);

    void saveEducationAndWork(Long userId, EducationAndWork educationAndWork);

    void saveContactInformation(Long userId, ContactInformation contactInformation);
}
