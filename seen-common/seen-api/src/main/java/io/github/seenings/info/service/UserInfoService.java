package io.github.seenings.info.service;

import io.github.seenings.info.model.BasicInformation;
import io.github.seenings.info.model.ContactInformation;
import io.github.seenings.info.model.EducationAndWork;
import io.github.seenings.info.model.UserMainInfo;

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
