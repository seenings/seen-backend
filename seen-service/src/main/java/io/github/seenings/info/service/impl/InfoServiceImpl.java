package io.github.seenings.info.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.address.http.HttpCityService;
import io.github.seenings.address.http.HttpProvinceService;
import io.github.seenings.article.enumeration.ContentType;
import io.github.seenings.chat.http.HttpChatHistoryService;
import io.github.seenings.chat.model.ChatContentAndTime;
import io.github.seenings.chat.model.UserChatInfo;
import io.github.seenings.time.util.DateUtil;
import io.github.seenings.core.util.NumberUtils;
import io.github.seenings.core.util.SetUtils;
import io.github.seenings.info.entity.Info;
import io.github.seenings.info.http.HttpUserAliasNameService;
import io.github.seenings.info.http.HttpUserBirthPlaceService;
import io.github.seenings.info.http.HttpUserBirthdayService;
import io.github.seenings.info.http.HttpUserCurrentResidenceService;
import io.github.seenings.info.http.HttpUserMainPhotoService;
import io.github.seenings.info.http.HttpUserSexService;
import io.github.seenings.info.http.HttpUserStatureService;
import io.github.seenings.info.http.HttpUserWeightService;
import io.github.seenings.info.http.HttpUserWorkPositionService;
import io.github.seenings.info.http.HttpWorkPositionService;
import io.github.seenings.info.mapper.InfoMapper;
import io.github.seenings.info.model.BasicInfo;
import io.github.seenings.info.model.PersonIntroduce;
import io.github.seenings.info.model.UserInfo;
import io.github.seenings.info.model.UserIntroduceInfo;
import io.github.seenings.info.service.InfoService;
import io.github.seenings.info.util.UserEnumUtils;
import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.http.HttpUserIntroducePhotoService;
import io.github.seenings.introduce.http.HttpUserIntroduceService;
import io.github.seenings.introduce.model.IntroduceTypeAndPhoto;
import io.github.seenings.introduce.model.IntroduceTypeAndText;
import io.github.seenings.introduce.util.IntroduceEnumUtils;
import io.github.seenings.school.http.HttpEducationalService;
import io.github.seenings.school.http.HttpSchoolGraduateService;
import io.github.seenings.school.http.HttpSchoolService;
import io.github.seenings.school.http.HttpStudentInfoService;
import io.github.seenings.school.util.SchoolEnumUtils;
import io.github.seenings.text.http.HttpTextService;
import io.github.seenings.work.http.HttpUserWorkService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Pair;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户信息 服务实现类
 */
@Slf4j
@Service
@AllArgsConstructor
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

    private HttpUserMainPhotoService httpUserMainPhotoService;

    /**
     * 根据用户ID获取用户信息
     *
     * @param userIds 用户ID
     * @return 用户ID对应用户信息
     */
    @Override
    public Map<Long, UserInfo> userIdToUserInfo(Set<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        Map<Long, Integer> userIdPhotoIdMap = httpUserMainPhotoService.userIdPhotoId(userIds);
        Map<Long, Integer> userIdToSexMap = httpUserSexService.userIdToSex(userIds);
        Map<Long, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
        if (userIdToSexMap == null) {
            String msg = String.format("用户不存在{}，用户ID：%s。", userIds);
            log.error("{}", msg);
            return null;
        }
        return userIds.stream().parallel().map(userId -> {
            UserInfo userInfo = new UserInfo(userId, Optional.ofNullable(userIdToAliasNameMap).map(n -> n.get(userId)).orElse(null), Optional.of(userIdToSexMap).map(n -> n.get(userId)).map(UserEnumUtils::indexToSexEnum).orElse(null), Optional.ofNullable(userIdPhotoIdMap).map(n -> n.get(userId)).orElse(null));
            return Pair.of(userId, userInfo);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    /**
     * 根据用户ID获取用户名字
     *
     * @param userIds 用户ID
     * @return 用户ID对应用户名字
     */
    @Override
    public Map<Long, String> userIdToUserName(Set<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(userIds), 100).parallelStream().flatMap(subs -> list(new QueryWrapper<Info>().lambda().in(Info::getUserId, subs).select(Info::getUserId, Info::getName)).stream()).collect(Collectors.toMap(Info::getUserId, Info::getName, (o1, o2) -> o2));
    }

    /**
     * 根据用户ID获取用户头像照片ID
     *
     * @param userIds 用户ID
     * @return 用户ID对应用户头像照片ID
     */
    @Override
    public Map<Long, Integer> userIdToProfilePhotoId(Set<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(userIds), 100).parallelStream().flatMap(subs -> list(new QueryWrapper<Info>().lambda().in(Info::getUserId, subs).select(Info::getUserId, Info::getProfilePhotoId)).stream()).collect(Collectors.toMap(Info::getUserId, Info::getProfilePhotoId, (o1, o2) -> o2));
    }


    private HttpUserAliasNameService httpUserAliasNameService;

    private HttpEducationalService httpEducationalService;

    private HttpUserBirthdayService httpUserBirthdayService;

    private HttpUserStatureService httpUserStatureService;

    private HttpUserWeightService httpUserWeightService;

    private HttpUserBirthPlaceService httpUserBirthPlaceService;

    private HttpUserCurrentResidenceService httpUserCurrentResidenceService;

    private HttpCityService httpCityService;

    private HttpProvinceService httpProvinceService;

    private HttpUserWorkPositionService httpUserWorkPositionService;

    private HttpWorkPositionService httpWorkPositionService;

    private HttpUserWorkService httpUserWorkService;

    private HttpStudentInfoService httpStudentInfoService;

    private HttpSchoolService httpSchoolService;

    @Override
    public Map<Long, UserIntroduceInfo> userIdToUserIntroduceInfo(@RequestBody Set<Long> userIds) {

        Map<Long, Integer> userIdToStatureCmMap = httpUserStatureService.userIdToStatureCm(userIds);
        Map<Long, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
        Map<Long, Integer> userIdToEducationalMap = httpEducationalService.userIdToEducational(userIds);
        Map<Long, Integer> userIdToYearMap = httpUserBirthdayService.userIdToYear(userIds);
        Map<Long, Integer> userIdToMonthMap = httpUserBirthdayService.userIdToMonth(userIds);
        Map<Long, Integer> userIdToDayMap = httpUserBirthdayService.userIdToDay(userIds);
        Map<Long, Integer> userIdToWeightKgMap = httpUserWeightService.userIdToWeightKg(userIds);
        Map<Long, Integer> userIdToBirthPlaceProvinceIdMap = httpUserBirthPlaceService.userIdToProvinceId(userIds);
        Map<Long, Integer> userIdToBirthPlaceCityIdMap = httpUserBirthPlaceService.userIdToCityId(userIds);
        Map<Long, Integer> userIdToCityIdMap = httpUserCurrentResidenceService.userIdToCityId(userIds);
        Set<Integer> cityIds = SetUtils.union(userIdToCityIdMap.values(), userIdToBirthPlaceCityIdMap.values());
        Map<Integer, String> cityIdToNameMap = httpCityService.idToName(cityIds);
        Map<Long, Integer> userIdToSchoolIdMap = httpStudentInfoService.userIdToSchoolId(userIds);
        Map<Integer, String> schoolIdToSchoolNameMap = httpSchoolService.idToSchoolName(new HashSet<>(userIdToSchoolIdMap.values()));

        Map<Integer, String> provinceIdToNameMap = httpProvinceService.idToName(new HashSet<>(userIdToBirthPlaceProvinceIdMap.values()));
        Map<Long, Integer> userIdToPositionMap = httpUserWorkPositionService.userIdToPosition(userIds);
        Map<Integer, String> positionIdToPositionNameMap = httpWorkPositionService.positionIdToPositionName(new HashSet<>(userIdToPositionMap.values()));
        Map<Long, String> userIdToCompanyNameMap = httpUserWorkService.userIdToCompanyName(userIds);
        return userIds.stream().parallel().map(userId -> {
            Integer birthPlaceCityId = userIdToBirthPlaceCityIdMap.get(userId);
            Integer cityId = userIdToCityIdMap.get(userId);
            Integer birthPlaceProvinceId = userIdToBirthPlaceProvinceIdMap.get(userId);
            Integer schoolId = userIdToSchoolIdMap.get(userId);
            String schoolName = schoolId == null ? null : schoolIdToSchoolNameMap.get(schoolId);
            String positionName = positionIdToPositionNameMap.get(userIdToPositionMap.get(userId));
            UserIntroduceInfo userIntroduceInfo = new UserIntroduceInfo().setUserId(userId).setUserAuthId(0) // TODO
                    .setAliasName(userIdToAliasNameMap.get(userId)).setEducationId(userIdToEducationalMap.get(userId)).setBirthYear(NumberUtils.intToString(userIdToYearMap.get(userId))).setConstellation(DateUtil.monthAndDayToConstellation(userIdToMonthMap.get(userId), userIdToDayMap.get(userId))).setStatureCm(NumberUtils.defaultIfNull(userIdToStatureCmMap.get(userId), 0)).setWeightKg(NumberUtils.defaultIfNull(userIdToWeightKgMap.get(userId), 0)).setBirthPlaceCityName(cityIdToNameMap.get(birthPlaceCityId)).setCurrentResidenceCityName(cityIdToNameMap.get(cityId)).setBirthPlaceProvinceName(provinceIdToNameMap.get(birthPlaceProvinceId)).setSchoolName(schoolName).setWorkPositionName(positionName).setWorkCompanyName(userIdToCompanyNameMap.get(userId));
            return Pair.of(userId, userIntroduceInfo);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    private HttpUserSexService httpUserSexService;

    private HttpSchoolGraduateService httpSchoolGraduateService;

    /**
     * 根据用户ID获取基本信息
     *
     * @param userIds 用户ID
     * @return 用户ID对应基本信息
     */
    @Override
    public Map<Long, BasicInfo> userIdToBasicInfo(Set<Long> userIds) {
        Map<Long, Integer> userIdToEducationalMap = httpEducationalService.userIdToEducational(userIds);
        Map<Long, Integer> userIdToSexMap = httpUserSexService.userIdToSex(userIds);
        Map<Long, Integer> userIdToYearMap = httpUserBirthdayService.userIdToYear(userIds);
        Map<Long, Integer> userIdToGraduatedMap = httpSchoolGraduateService.userIdToGraduated(userIds);
        return userIds.stream().parallel().map(userId -> Pair.of(userId, new BasicInfo().setUserId(userId).setSex(UserEnumUtils.indexToSexEnum(userIdToSexMap.get(userId))).setEducation(SchoolEnumUtils.indexToEducationEnum(userIdToEducationalMap.get(userId))).setBirthYear(userIdToYearMap.get(userId)).setGraduated(userIdToGraduatedMap.get(userId) == null ? null : userIdToGraduatedMap.get(userId) == 1))).collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    private HttpUserIntroduceService httpUserIntroduceService;

    private HttpUserIntroducePhotoService httpUserIntroducePhotoService;

    private HttpTextService httpTextService;

    @Override
    public Map<Long, List<PersonIntroduce>> userIdToPersonIntroduce(Set<Long> userIds) {

        Map<Long, Set<IntroduceTypeAndText>> userIdToIntroduceTypeAndTextMap = httpUserIntroduceService.userIdToIntroduceTypeAndText(userIds);
        Map<Long, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhotoMap = httpUserIntroducePhotoService.userIdToIntroduceTypeAndPhoto(userIds);
        Set<Integer> textIds = userIdToIntroduceTypeAndTextMap.values().stream().parallel().flatMap(Collection::stream).map(IntroduceTypeAndText::textId).collect(Collectors.toSet());
        Map<Integer, String> textIdToTextMap = httpTextService.textIdToText(textIds);
        return userIds.stream().parallel().map(userId -> {
            Set<IntroduceTypeAndPhoto> introduceTypeAndPhotos = userIdToIntroduceTypeAndPhotoMap.get(userId);
            Set<IntroduceTypeAndText> introduceTypeAndTexts = userIdToIntroduceTypeAndTextMap.get(userId);
            Map<IntroduceTypeEnum, Set<IntroduceTypeAndPhoto>> introduceTypeEnumToIntroduceTypeAndPhotoMap = introduceTypeAndPhotos == null ? Collections.emptyMap() : introduceTypeAndPhotos.stream().parallel().filter(n -> n.introduceTypeEnum() != IntroduceTypeEnum.MAIN_PAGE).collect(Collectors.groupingBy(IntroduceTypeAndPhoto::introduceTypeEnum, Collectors.toSet()));

            Map<IntroduceTypeEnum, Integer> introudceTypeEnumToTextIdMap = introduceTypeAndTexts == null ? Collections.emptyMap() : introduceTypeAndTexts.stream().parallel().filter(n -> IntroduceTypeEnum.MAIN_PAGE != n.introduceTypeEnum()).collect(Collectors.toMap(IntroduceTypeAndText::introduceTypeEnum, IntroduceTypeAndText::textId));
            List<PersonIntroduce> personIntroduces = Arrays.stream(IntroduceTypeEnum.values()).filter(n -> n != IntroduceTypeEnum.MAIN_PAGE).map(n -> {
                Set<IntroduceTypeAndPhoto> introduceTypeAndPhotoBySingle = introduceTypeEnumToIntroduceTypeAndPhotoMap.get(n);
                Map<Integer, Integer> orderToPhotoIdMap = introduceTypeAndPhotoBySingle == null ? Collections.emptyMap() : introduceTypeAndPhotoBySingle.stream().parallel().collect(Collectors.toMap(IntroduceTypeAndPhoto::order, IntroduceTypeAndPhoto::photoId));
                Integer textId = introudceTypeEnumToTextIdMap.get(n);
                String introduceContent = textId == null ? null : textIdToTextMap.get(textId);
                return new PersonIntroduce(n.getIndex(), n.getName(), introduceContent, orderToPhotoIdMap);
            }).collect(Collectors.toList());
            return Pair.of(userId, personIntroduces);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    /**
     * 根据介绍类型获取介绍内容
     *
     * @param introduceTypeIndexes 介绍类型
     * @param userId               用户ID
     * @return 介绍类型对应介绍内容
     */
    @Override
    public Map<Integer, PersonIntroduce> introduceTypeIndexToPersonIntroduce(Set<Integer> introduceTypeIndexes, Long userId) {
        Set<IntroduceTypeAndText> introduceTypeAndTexts = Optional.ofNullable(httpUserIntroduceService.userIdToIntroduceTypeAndText(Set.of(userId))).map(n -> n.get(userId)).orElseGet(CollUtil::newHashSet);

        Set<IntroduceTypeAndPhoto> introduceTypeAndPhotos = Optional.ofNullable(httpUserIntroducePhotoService.userIdToIntroduceTypeAndPhoto(Set.of(userId))).map(n -> n.get(userId)).orElseGet(CollUtil::newHashSet);
        Set<Integer> textIds = introduceTypeAndTexts.stream().parallel().map(IntroduceTypeAndText::textId).collect(Collectors.toSet());

        Map<Integer, String> textIdToTextMap = httpTextService.textIdToText(textIds);
        return introduceTypeIndexes.stream().parallel().map(introduceTypeIndex -> {
            IntroduceTypeEnum introduceTypeEnum = IntroduceEnumUtils.indexToIntroduceTypeEnum(introduceTypeIndex);
            Integer textId = introduceTypeAndTexts.stream().parallel().filter(n -> n.introduceTypeEnum() == introduceTypeEnum).map(IntroduceTypeAndText::textId).findFirst().orElse(null);
            String introduceContent = textId == null ? null : textIdToTextMap.get(textId);
            Map<Integer, Integer> orderToPhotoIdMap = introduceTypeAndPhotos.stream().parallel().filter(n -> n.introduceTypeEnum() == introduceTypeEnum).collect(Collectors.toMap(IntroduceTypeAndPhoto::order, IntroduceTypeAndPhoto::photoId));
            return Pair.of(introduceTypeIndex, new PersonIntroduce(introduceTypeIndex, introduceTypeEnum.getName(), introduceContent, orderToPhotoIdMap));
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private HttpChatHistoryService httpChatHistoryService;

    @Override
    public Map<Long, UserChatInfo> userIdToUserChatInfo(Set<Long> userIds, Long selfUserId) {

        Map<Long, UserInfo> userIdToUserInfoMap = userIdToUserInfo(userIds);
        Map<Long, ChatContentAndTime> userIdToChatContentAndTimeMap = httpChatHistoryService.userIdToChatContentAndTime(userIds, selfUserId);
        Set<Integer> textIds = userIdToChatContentAndTimeMap.values().stream().parallel().filter(n -> n.contentType() == ContentType.TEXT).map(ChatContentAndTime::contentId).collect(Collectors.toSet());
        Map<Integer, String> textIdToTextMap = httpTextService.textIdToText(textIds);
        return userIds.stream().parallel().map(n -> {
            UserInfo userInfo = userIdToUserInfoMap.get(n);
            ChatContentAndTime chatContentAndTime = userIdToChatContentAndTimeMap.get(n);
            if (chatContentAndTime == null) {
                return null;
            }
            String newestChatText;
            switch (chatContentAndTime.contentType()) {
                case TEXT -> newestChatText = textIdToTextMap.get(chatContentAndTime.contentId());
                case IMAGE -> newestChatText = "[图片]";
                case VOICE -> newestChatText = "[语音]";
                case VIDEO -> newestChatText = "[视频]";
                default -> newestChatText = "[其他]";
            }
            LocalDateTime newestChatTime = chatContentAndTime.sendTime();
            UserChatInfo userChatInfo = new UserChatInfo(n, userInfo.aliasName(), userInfo.sex(), userInfo.mainPhotoId(), newestChatText, newestChatTime);
            return Map.entry(n, userChatInfo);
        }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o2));
    }
}
