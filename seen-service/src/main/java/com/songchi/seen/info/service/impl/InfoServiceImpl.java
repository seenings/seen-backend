package com.songchi.seen.info.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.address.http.HttpCityService;
import com.songchi.seen.address.http.HttpProvinceService;
import com.songchi.seen.article.enumeration.ContentType;
import com.songchi.seen.chat.http.HttpChatHistoryService;
import com.songchi.seen.chat.model.ChatContentAndTime;
import com.songchi.seen.chat.model.UserChatInfo;
import com.songchi.seen.core.util.DateUtils;
import com.songchi.seen.core.util.NumberUtils;
import com.songchi.seen.core.util.SetUtils;
import com.songchi.seen.info.entity.Info;
import com.songchi.seen.info.enumeration.Sex;
import com.songchi.seen.info.http.HttpUserAliasNameService;
import com.songchi.seen.info.http.HttpUserBirthPlaceService;
import com.songchi.seen.info.http.HttpUserBirthdayService;
import com.songchi.seen.info.http.HttpUserCurrentResidenceService;
import com.songchi.seen.info.http.HttpUserMainPhotoService;
import com.songchi.seen.info.http.HttpUserSexService;
import com.songchi.seen.info.http.HttpUserStatureService;
import com.songchi.seen.info.http.HttpUserWeightService;
import com.songchi.seen.info.http.HttpUserWorkPositionService;
import com.songchi.seen.info.http.HttpWorkPositionService;
import com.songchi.seen.info.mapper.InfoMapper;
import com.songchi.seen.info.model.BasicInfo;
import com.songchi.seen.info.model.PersonIntroduce;
import com.songchi.seen.info.model.UserInfo;
import com.songchi.seen.info.model.UserIntroduceInfo;
import com.songchi.seen.info.service.InfoService;
import com.songchi.seen.info.util.UserEnumUtils;
import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.http.HttpUserIntroducePhotoService;
import com.songchi.seen.introduce.http.HttpUserIntroduceService;
import com.songchi.seen.introduce.model.IntroduceTypeAndPhoto;
import com.songchi.seen.introduce.model.IntroduceTypeAndText;
import com.songchi.seen.introduce.util.IntroduceEnumUtils;
import com.songchi.seen.school.http.HttpEducationalService;
import com.songchi.seen.school.http.HttpSchoolGraduateService;
import com.songchi.seen.school.http.HttpSchoolService;
import com.songchi.seen.school.http.HttpStudentInfoService;
import com.songchi.seen.school.util.SchoolEnumUtils;
import com.songchi.seen.text.http.HttpTextService;
import com.songchi.seen.work.http.HttpUserWorkService;

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
    public Map<Integer, UserInfo> userIdToUserInfo(Set<Integer> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        Map<Integer, Integer> userIdPhotoIdMap = httpUserMainPhotoService.userIdPhotoId(userIds);
        Map<Integer, Integer> userIdToSexMap = httpUserSexService.userIdToSex(userIds);
        Map<Integer, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
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
    public Map<Integer, String> userIdToUserName(Set<Integer> userIds) {
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
    public Map<Integer, Integer> userIdToProfilePhotoId(Set<Integer> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(userIds), 100).parallelStream().flatMap(subs -> list(new QueryWrapper<Info>().lambda().in(Info::getUserId, subs).select(Info::getUserId, Info::getProfilePhotoId)).stream()).collect(Collectors.toMap(Info::getUserId, Info::getProfilePhotoId, (o1, o2) -> o2));
    }

    /**
     * 根据用户ID获取性别
     *
     * @param userIds 用户ID
     * @return 用户ID对应性别
     */
    @Override
    public Map<Integer, Sex> userIdToSex(Set<Integer> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(userIds), 100).parallelStream().flatMap(subs -> list(new QueryWrapper<Info>().lambda().in(Info::getUserId, subs).select(Info::getUserId, Info::getSex)).stream()).collect(Collectors.toMap(Info::getUserId, n -> UserEnumUtils.indexToSexEnum(n.getSex()), (o1, o2) -> o2));
    }

    /**
     * 获取最新注册的用户，填写了用户信息
     *
     * @param top 个数
     * @return 用户ID对应填写用户信息时间
     */
    @Override
    public Map<Integer, LocalDateTime> newUserId(int top) {
        Page<Info> page = page(new Page<>(1, top), new QueryWrapper<Info>().lambda().select(Info::getUserId, Info::getCreateTime).orderByDesc(Info::getCreateTime));
        return page.getRecords().stream().parallel().collect(Collectors.toMap(Info::getUserId, Info::getCreateTime, (o1, o2) -> o2));
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
    public Map<Integer, UserIntroduceInfo> userIdToUserIntroduceInfo(@RequestBody Set<Integer> userIds) {

        Map<Integer, Integer> userIdToStatureCmMap = httpUserStatureService.userIdToStatureCm(userIds);
        Map<Integer, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
        Map<Integer, Integer> userIdToEducationalMap = httpEducationalService.userIdToEducational(userIds);
        Map<Integer, Integer> userIdToYearMap = httpUserBirthdayService.userIdToYear(userIds);
        Map<Integer, Integer> userIdToMonthMap = httpUserBirthdayService.userIdToMonth(userIds);
        Map<Integer, Integer> userIdToDayMap = httpUserBirthdayService.userIdToDay(userIds);
        Map<Integer, Integer> userIdToWeightKgMap = httpUserWeightService.userIdToWeightKg(userIds);
        Map<Integer, Integer> userIdToBirthPlaceProvinceIdMap = httpUserBirthPlaceService.userIdToProvinceId(userIds);
        Map<Integer, Integer> userIdToBirthPlaceCityIdMap = httpUserBirthPlaceService.userIdToCityId(userIds);
        Map<Integer, Integer> userIdToCityIdMap = httpUserCurrentResidenceService.userIdToCityId(userIds);
        Set<Integer> cityIds = SetUtils.union(userIdToCityIdMap.values(), userIdToBirthPlaceCityIdMap.values());
        Map<Integer, String> cityIdToNameMap = httpCityService.idToName(cityIds);
        Map<Integer, Integer> userIdToSchoolIdMap = httpStudentInfoService.userIdToSchoolId(userIds);
        Map<Integer, String> schoolIdToSchoolNameMap = httpSchoolService.idToSchoolName(new HashSet<>(userIdToSchoolIdMap.values()));

        Map<Integer, String> provinceIdToNameMap = httpProvinceService.idToName(new HashSet<>(userIdToBirthPlaceProvinceIdMap.values()));
        Map<Integer, Integer> userIdToPositionMap = httpUserWorkPositionService.userIdToPosition(userIds);
        Map<Integer, String> positionIdToPositionNameMap = httpWorkPositionService.positionIdToPositionName(new HashSet<>(userIdToPositionMap.values()));
        Map<Integer, String> userIdToCompanyNameMap = httpUserWorkService.userIdToCompanyName(userIds);
        return userIds.stream().parallel().map(userId -> {
            Integer birthPlaceCityId = userIdToBirthPlaceCityIdMap.get(userId);
            Integer cityId = userIdToCityIdMap.get(userId);
            Integer birthPlaceProvinceId = userIdToBirthPlaceProvinceIdMap.get(userId);
            Integer schoolId = userIdToSchoolIdMap.get(userId);
            String schoolName = schoolId == null ? null : schoolIdToSchoolNameMap.get(schoolId);
            String positionName = positionIdToPositionNameMap.get(userIdToPositionMap.get(userId));
            UserIntroduceInfo userIntroduceInfo = new UserIntroduceInfo().setUserId(userId).setUserAuthId(0) // TODO
                    .setAliasName(userIdToAliasNameMap.get(userId)).setEducationId(userIdToEducationalMap.get(userId)).setBirthYear(NumberUtils.intToString(userIdToYearMap.get(userId))).setConstellation(DateUtils.monthAndDayToConstellation(userIdToMonthMap.get(userId), userIdToDayMap.get(userId))).setStatureCm(NumberUtils.defaultIfNull(userIdToStatureCmMap.get(userId), 0)).setWeightKg(NumberUtils.defaultIfNull(userIdToWeightKgMap.get(userId), 0)).setBirthPlaceCityName(cityIdToNameMap.get(birthPlaceCityId)).setCurrentResidenceCityName(cityIdToNameMap.get(cityId)).setBirthPlaceProvinceName(provinceIdToNameMap.get(birthPlaceProvinceId)).setSchoolName(schoolName).setWorkPositionName(positionName).setWorkCompanyName(userIdToCompanyNameMap.get(userId));
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
    public Map<Integer, BasicInfo> userIdToBasicInfo(Set<Integer> userIds) {
        Map<Integer, Integer> userIdToEducationalMap = httpEducationalService.userIdToEducational(userIds);
        Map<Integer, Integer> userIdToSexMap = httpUserSexService.userIdToSex(userIds);
        Map<Integer, Integer> userIdToYearMap = httpUserBirthdayService.userIdToYear(userIds);
        Map<Integer, Integer> userIdToGraduatedMap = httpSchoolGraduateService.userIdToGraduated(userIds);
        return userIds.stream().parallel().map(userId -> Pair.of(userId, new BasicInfo().setUserId(userId).setSex(UserEnumUtils.indexToSexEnum(userIdToSexMap.get(userId))).setEducation(SchoolEnumUtils.indexToEducationEnum(userIdToEducationalMap.get(userId))).setBirthYear(userIdToYearMap.get(userId)).setGraduated(userIdToGraduatedMap.get(userId) == null ? null : userIdToGraduatedMap.get(userId) == 1))).collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    private HttpUserIntroduceService httpUserIntroduceService;

    private HttpUserIntroducePhotoService httpUserIntroducePhotoService;

    private HttpTextService httpTextService;

    @Override
    public Map<Integer, List<PersonIntroduce>> userIdToPersonIntroduce(Set<Integer> userIds) {

        Map<Integer, Set<IntroduceTypeAndText>> userIdToIntroduceTypeAndTextMap = httpUserIntroduceService.userIdToIntroduceTypeAndText(userIds);
        Map<Integer, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhotoMap = httpUserIntroducePhotoService.userIdToIntroduceTypeAndPhoto(userIds);
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
    public Map<Integer, PersonIntroduce> introduceTypeIndexToPersonIntroduce(Set<Integer> introduceTypeIndexes, Integer userId) {
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
    public Map<Integer, UserChatInfo> userIdToUserChatInfo(Set<Integer> userIds, Integer selfUserId) {

        Map<Integer, UserInfo> userIdToUserInfoMap = userIdToUserInfo(userIds);
        Map<Integer, ChatContentAndTime> userIdToChatContentAndTimeMap = httpChatHistoryService.userIdToChatContentAndTime(userIds, selfUserId);
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
