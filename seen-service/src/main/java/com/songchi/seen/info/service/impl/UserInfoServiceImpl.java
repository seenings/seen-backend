package com.songchi.seen.info.service.impl;

import cn.hutool.core.util.StrUtil;
import com.songchi.seen.address.http.HttpCityService;
import com.songchi.seen.address.http.HttpProvinceService;
import com.songchi.seen.core.util.StrUtils;
import com.songchi.seen.info.converter.UserInfoConverter;
import com.songchi.seen.info.http.*;
import com.songchi.seen.info.model.BasicInformation;
import com.songchi.seen.info.model.ContactInformation;
import com.songchi.seen.info.model.EducationAndWork;
import com.songchi.seen.info.model.UserMainInfo;
import com.songchi.seen.info.service.UserInfoService;
import com.songchi.seen.info.util.UserEnumUtils;
import com.songchi.seen.photo.http.HttpPhotoService;
import com.songchi.seen.school.enumeration.Education;
import com.songchi.seen.school.http.HttpEducationalService;
import com.songchi.seen.school.http.HttpStudentInfoService;
import com.songchi.seen.school.util.SchoolEnumUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.work.http.HttpUserIncomeService;
import com.songchi.seen.work.http.HttpUserWorkService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * UserInfoServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private HttpUserAliasNameService httpUserAliasNameService;

    @Resource
    private HttpUserMainPhotoService httpUserMainPhotoService;

    @Resource
    private HttpUserAuthService httpUserAuthService;

    @Resource
    private HttpUserBirthdayService httpUserBirthdayService;

    @Resource
    private HttpUserWeightService httpUserWeightService;

    @Resource
    private HttpUserBirthPlaceService httpUserBirthPlaceService;

    @Resource
    private HttpUserStatureService httpUserStatureService;

    @Resource
    private HttpUserCurrentResidenceService httpUserCurrentResidenceService;

    @Resource
    private HttpUserWorkPositionService httpUserWorkPositionService;

    @Resource
    private HttpWorkPositionService httpWorkPositionService;

    @Resource
    private HttpCityService httpCityService;

    @Resource
    private HttpProvinceService httpProvinceService;

    @Resource
    private HttpEducationalService httpEducationalService;

    @Resource
    private HttpPhotoService httpPhotoService;

    @Override
    public Map<Integer, UserMainInfo> userIdToUserMainInfo(Set<Integer> userIds) {
        Map<Integer, Integer> userIdPhotoIdMap = httpUserMainPhotoService.userIdPhotoId(userIds);

        Map<Integer, String> photoIdToPhotoUrlMap =
                httpPhotoService.photoIdToPhotoUrl(new HashSet<>(userIdPhotoIdMap.values()));
        Map<Integer, Integer> userIdToUserAuthMap = httpUserAuthService.userIdToUserAuth(userIds);
        Map<Integer, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
        Map<Integer, Integer> userIdToCurrentResidenceProvinceIdMap =
                httpUserCurrentResidenceService.userIdToProvinceId(userIds);
        Map<Integer, Integer> userIdToCurrentResidenceCityIdMap =
                httpUserCurrentResidenceService.userIdToCityId(userIds);
        Map<Integer, Integer> userIdToBirthPlaceProvinceIdMap = httpUserBirthPlaceService.userIdToProvinceId(userIds);
        Map<Integer, Integer> userIdToBirthPlaceCityIdMap = httpUserBirthPlaceService.userIdToCityId(userIds);
        Set<Integer> cityIds = new HashSet<>(userIdToBirthPlaceCityIdMap.values());
        cityIds.addAll(new HashSet<>(userIdToCurrentResidenceCityIdMap.values()));
        Map<Integer, String> cityToNameMap = httpCityService.idToName(cityIds);
        Set<Integer> provinceIds = new HashSet<>(userIdToBirthPlaceProvinceIdMap.values());
        provinceIds.addAll(new HashSet<>(userIdToCurrentResidenceProvinceIdMap.values()));
        Map<Integer, String> provinceToNameMap = httpProvinceService.idToName(provinceIds);
        Map<Integer, Integer> userIdToYearMap = httpUserBirthdayService.userIdToYear(userIds);
        Map<Integer, Integer> userIdToWeightKgMap = httpUserWeightService.userIdToWeightKg(userIds);
        Map<Integer, Integer> userIdToStatureCmMap = httpUserStatureService.userIdToStatureCm(userIds);
        Map<Integer, Integer> userIdToPositionMap = httpUserWorkPositionService.userIdToPosition(userIds);
        Map<Integer, String> positionIdToPositionNameMap =
                httpWorkPositionService.positionIdToPositionName(new HashSet<>(userIdToPositionMap.values()));
        Map<Integer, Integer> userIdToEducationalMap = httpEducationalService.userIdToEducational(userIds);

        return userIds.stream()
                .parallel()
                .collect(Collectors.toMap(
                        Function.identity(),
                        userId -> {
                            Integer userAuth = userIdToUserAuthMap.get(userId);
                            Integer currentProvinceId = userIdToCurrentResidenceProvinceIdMap.get(userId);
                            String currentProvinceName = provinceToNameMap.get(currentProvinceId);
                            Integer birthProvinceId = userIdToBirthPlaceProvinceIdMap.get(userId);
                            String birthProvinceName = provinceToNameMap.get(birthProvinceId);

                            Integer currentCityId = userIdToCurrentResidenceCityIdMap.get(userId);
                            String currentCityName = cityToNameMap.get(currentCityId);
                            Integer birthCityId = userIdToBirthPlaceCityIdMap.get(userId);
                            String birthCityName = cityToNameMap.get(birthCityId);

                            Integer positionId = userIdToPositionMap.get(userId);
                            String positionName = positionIdToPositionNameMap.get(positionId);
                            Integer educationalId = userIdToEducationalMap.get(userId);
                            Education education = SchoolEnumUtils.indexToEducationEnum(educationalId);
                            Integer photoId = userIdPhotoIdMap.get(userId);
                            String shortPhotoUrl = photoIdToPhotoUrlMap.get(photoId);
                            String photoUrl = StrUtil.isBlank(shortPhotoUrl)
                                    ? null
                                    : PublicConstant.PHOTO_VERSION + shortPhotoUrl;
                            return UserInfoConverter.INSTANCE.convert(
                                    userId,
                                    photoId,
                                    photoUrl,
                                    userAuth,
                                    userIdToAliasNameMap.get(userId),
                                    currentProvinceName,
                                    currentCityName,
                                    birthProvinceName,
                                    birthCityName,
                                    userIdToYearMap.get(userId),
                                    userIdToWeightKgMap.get(userId),
                                    userIdToStatureCmMap.get(userId),
                                    education.getIndex(),
                                    positionName);
                        },
                        (o1, o2) -> o2));
    }

    @Resource
    private HttpUserSexService httpUserSexService;

    @Resource
    private HttpUserMaritalService httpUserMaritalService;

    @Resource
    private HttpUserWorkService httpUserWorkService;

    @Override
    public void saveBasicInformation(Integer userId, BasicInformation basicInformation) {

        String aliasName = basicInformation.aliasName();
        LocalDate birthDate = basicInformation.birthDate();
        Integer sex = basicInformation.sex();
        Integer stature = basicInformation.stature();
        Integer weight = basicInformation.weight();
        List<String> currentResidence = basicInformation.currentResidence();
        List<String> birthPlace = basicInformation.birthPlace();
        Integer maritalStatus = basicInformation.maritalStatus();
        httpUserAliasNameService.set(userId, aliasName);
        httpUserBirthdayService.set(userId, birthDate.getYear(), birthDate.getMonthValue(), birthDate.getDayOfMonth());
        httpUserSexService.set(userId, UserEnumUtils.indexToSexEnum(sex));
        httpUserStatureService.set(userId, stature);
        httpUserWeightService.set(userId, weight);
        httpUserCurrentResidenceService.set(
                userId, StrUtils.stringToInt(currentResidence.get(0)), StrUtils.stringToInt(currentResidence.get(1)));
        httpUserBirthPlaceService.set(
                userId, StrUtils.stringToInt(birthPlace.get(0)), StrUtils.stringToInt(birthPlace.get(1)));
        httpUserMaritalService.set(userId, maritalStatus);
    }

    @Resource
    private HttpStudentInfoService httpStudentInfoService;

    @Resource
    private HttpUserIncomeService httpUserIncomeService;

    @Override
    public void saveEducationAndWork(Integer userId, EducationAndWork educationAndWork) {

        httpEducationalService.set(userId, SchoolEnumUtils.indexToEducationEnum(educationAndWork.highestEducation()));
        httpStudentInfoService.set(
                userId, StrUtils.stringToInt(educationAndWork.highestSchoolIds().get(1)));
        httpUserWorkPositionService.set(userId, educationAndWork.workPositionId());
        httpUserWorkService.set(userId, educationAndWork.workCompany());
        httpUserIncomeService.set(userId, UserEnumUtils.indexToYearIncomeEnum(educationAndWork.annualIncomeIndex()));
    }

    @Override
    public void saveContactInformation(Integer userId, ContactInformation contactInformation) {

        // 联系电话不能在这里修改
    }
}
