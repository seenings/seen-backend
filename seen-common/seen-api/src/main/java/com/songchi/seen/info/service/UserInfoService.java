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
    Map<Integer, UserMainInfo> userIdToUserMainInfo(Set<Integer> userIds);

    void saveBasicInformation(Integer userId, BasicInformation basicInformation);

    void saveEducationAndWork(Integer userId, EducationAndWork educationAndWork);

    void saveContactInformation(Integer userId, ContactInformation contactInformation);
}
