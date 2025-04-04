package com.songchi.seen.info.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.songchi.seen.address.http.HttpProvinceService;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.core.util.NumberUtils;
import com.songchi.seen.info.http.HttpUserAliasNameService;
import com.songchi.seen.info.http.HttpUserBirthPlaceService;
import com.songchi.seen.info.http.HttpUserBirthdayService;
import com.songchi.seen.info.http.HttpUserCurrentResidenceService;
import com.songchi.seen.info.http.HttpUserMainPhotoService;
import com.songchi.seen.info.http.HttpUserMaritalService;
import com.songchi.seen.info.http.HttpUserService;
import com.songchi.seen.info.http.HttpUserSexService;
import com.songchi.seen.info.http.HttpUserStatureService;
import com.songchi.seen.info.http.HttpUserWeightService;
import com.songchi.seen.info.http.HttpUserWorkPositionService;
import com.songchi.seen.info.model.BasicInformation;
import com.songchi.seen.info.model.BasicInformationAll;
import com.songchi.seen.info.model.ContactInformation;
import com.songchi.seen.info.model.EducationAndWork;
import com.songchi.seen.info.model.PhotoUrl;
import com.songchi.seen.info.service.UserInfoService;
import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.http.HttpUserIntroducePhotoService;
import com.songchi.seen.introduce.model.IntroduceTypeAndPhoto;
import com.songchi.seen.introduce.model.OrderAndPhotoId;
import com.songchi.seen.introduce.util.IntroduceEnumUtils;
import com.songchi.seen.photo.http.HttpPhotoService;
import com.songchi.seen.school.http.HttpEducationalService;
import com.songchi.seen.school.http.HttpSchoolService;
import com.songchi.seen.school.http.HttpStudentInfoService;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.text.http.HttpTagService;
import com.songchi.seen.work.http.HttpUserIncomeService;
import com.songchi.seen.work.http.HttpUserWorkService;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户信息 前端控制器
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.REST + "user/self-info")
public class SelfInfoController {

    private HttpTagService httpTagService;

    private HttpUserAliasNameService httpUserAliasNameService;

    private HttpUserBirthdayService httpUserBirthdayService;

    private HttpUserSexService httpUserSexService;

    private HttpUserStatureService httpUserStatureService;

    private HttpUserWeightService httpUserWeightService;

    private HttpUserCurrentResidenceService httpUserCurrentResidenceService;

    private HttpUserBirthPlaceService httpUserBirthPlaceService;

    private HttpUserMaritalService httpUserMaritalService;

    private HttpEducationalService httpEducationalService;

    private HttpStudentInfoService httpStudentInfoService;

    private HttpSchoolService httpSchoolService;

    private HttpUserWorkPositionService httpUserWorkPositionService;

    private HttpUserWorkService httpUserWorkService;

    private HttpUserIncomeService httpUserIncomeService;

    private HttpProvinceService httpProvinceService;

    private HttpUserMainPhotoService httpUserMainPhotoService;

    private HttpPhotoService httpPhotoService;

    private HttpUserIntroducePhotoService httpUserIntroducePhotoService;

    @PostMapping("self-user-id-to-main-photo")
    public ResponseEntity<R<PhotoUrl>> selfUserIdToMainPhoto(@SessionAttribute Long userId) {
        Integer photoId = httpUserMainPhotoService.userIdPhotoId(Collections.singleton(userId)).get(userId);
        if (photoId == null) {
            return ResponseEntity.noContent().build();
        }
        String longPhotoUrl = httpPhotoService.photoIdToLongPhotoUrl(Collections.singleton(photoId)).get(photoId);
        PhotoUrl result = new PhotoUrl(photoId, longPhotoUrl);
        return ResponseEntity.ok(ResUtils.ok(result));
    }

    /**
     * 顺序对应主页照片ID
     *
     * @param userId 用户ID
     * @return 结果
     */
    @PostMapping("self-user-id-to-main-page-photo")
    public R<Map<Integer, Integer>> selfUserIdToMainPagePhoto(@SessionAttribute Long userId) {
        Set<IntroduceTypeAndPhoto> introduceTypeAndPhotos = httpUserIntroducePhotoService.userIdToIntroduceTypeAndPhoto(Collections.singleton(userId)).get(userId);
        Map<Integer, Integer> map = Optional.ofNullable(introduceTypeAndPhotos).map(m -> m.stream().parallel().filter(n -> n.introduceTypeEnum() == IntroduceTypeEnum.MAIN_PAGE).collect(Collectors.toMap(IntroduceTypeAndPhoto::order, IntroduceTypeAndPhoto::photoId))).orElseGet(Collections::emptyMap);
        return ResUtils.ok(map);
    }

    @PostMapping("self-user-id-to-education-and-work")
    public R<EducationAndWork> selfUserIdToEducationAndWork(@SessionAttribute Long userId) {

        Integer highestEducation = httpEducationalService.userIdToEducational(Collections.singleton(userId)).get(userId);
        Integer schoolId = httpStudentInfoService.userIdToSchoolId(Collections.singleton(userId)).get(userId);
        List<String> highestSchoolId;
        if (schoolId == null) {
            highestSchoolId = null;
        } else {
            String provinceCode = httpSchoolService.schoolIdToProvinceCode(Collections.singleton(schoolId)).get(schoolId);
            Integer provinceId = httpProvinceService.provinceCodeToProvinceId(Collections.singleton(provinceCode)).get(provinceCode);
            highestSchoolId = CollUtil.newArrayList(NumberUtils.intToString(provinceId), NumberUtils.intToString(schoolId));
        }
        Integer workPositionId = httpUserWorkPositionService.userIdToPosition(Collections.singleton(userId)).get(userId);
        String workCompany = httpUserWorkService.userIdToCompanyName(Collections.singleton(userId)).get(userId);
        Integer annualIncomeIndex = httpUserIncomeService.userIdToAnnualIncome(Collections.singleton(userId)).get(userId);
        EducationAndWork result = new EducationAndWork(highestEducation, highestSchoolId, workPositionId, workCompany, annualIncomeIndex);
        return ResUtils.ok(result);
    }

    @PostMapping("self-user-id-to-basic-information")
    public R<BasicInformation> selfUserIdToBasicInformation(@SessionAttribute Long userId) {

        String aliasName = httpUserAliasNameService.userIdToAliasName(Collections.singleton(userId)).get(userId);
        Integer year = httpUserBirthdayService.userIdToYear(Collections.singleton(userId)).get(userId);
        Integer month = httpUserBirthdayService.userIdToMonth(Collections.singleton(userId)).get(userId);
        Integer day = httpUserBirthdayService.userIdToDay(Collections.singleton(userId)).get(userId);
        LocalDate birthDate = year == null || month == null || day == null ? null : LocalDate.of(year, month, day);
        Integer sex = httpUserSexService.userIdToSex(Collections.singleton(userId)).get(userId);
        Integer stature = httpUserStatureService.userIdToStatureCm(Collections.singleton(userId)).get(userId);
        Integer weight = httpUserWeightService.userIdToWeightKg(Collections.singleton(userId)).get(userId);
        Integer currentResidenceProvinceId = httpUserCurrentResidenceService.userIdToProvinceId(Collections.singleton(userId)).get(userId);
        Integer currentResidenceCityId = httpUserCurrentResidenceService.userIdToCityId(Collections.singleton(userId)).get(userId);

        Integer birthPlaceProvinceId = httpUserBirthPlaceService.userIdToProvinceId(Collections.singleton(userId)).get(userId);
        Integer birthPlaceCityId = httpUserBirthPlaceService.userIdToCityId(Collections.singleton(userId)).get(userId);

        List<String> currentResidence = CollUtil.newArrayList(currentResidenceProvinceId, currentResidenceCityId).stream().map(NumberUtils::intToString).collect(Collectors.toList());
        List<String> birthPlace = CollUtil.newArrayList(birthPlaceProvinceId, birthPlaceCityId).stream().map(NumberUtils::intToString).collect(Collectors.toList());
        Integer maritalStatus = httpUserMaritalService.userIdToMaritalStatus(Collections.singleton(userId)).get(userId);

        BasicInformation result = new BasicInformation(aliasName, birthDate, sex, stature, weight, currentResidence, birthPlace, maritalStatus);
        return ResUtils.ok(result);
    }

    private HttpUserService httpUserService;

    @PostMapping("self-user-id-to-contact-information")
    public R<ContactInformation> selfUserIdToContactInformation(@SessionAttribute Long userId) {
        String photoNumber = httpUserService.userIdToPhoneNumber(Collections.singleton(userId)).get(userId);
        ContactInformation result = new ContactInformation(null, photoNumber);
        return ResUtils.ok(result);
    }

    @GetMapping("get-user-id")
    public R<Long> getUserId(@SessionAttribute Long userId) {
        return ResUtils.ok(userId);
    }

    @PostMapping("check-self")
    public R<Boolean> checkSelf(@RequestParam Long pageUserId, @SessionAttribute Long userId) {
        return ResUtils.ok(Objects.equals(pageUserId, userId));
    }

    private UserInfoService userInfoService;

    @PostMapping("save-basic-information-all")
    public R<Boolean> saveBasicInformationAll(@RequestBody BasicInformationAll basicInformationAll, @SessionAttribute Long userId) {
        userInfoService.saveBasicInformation(userId, basicInformationAll.basicInformation());
        userInfoService.saveEducationAndWork(userId, basicInformationAll.educationAndWork());
        userInfoService.saveContactInformation(userId, basicInformationAll.contactInformation());
        return ResUtils.ok(true);
    }

    @PostMapping("save-tag")
    public R<List<Integer>> saveTag(@RequestBody List<Integer> tagIds, @SessionAttribute Long userId) {
        List<Integer> result = httpTagService.deleteAndSave(userId, tagIds);
        return ResUtils.ok(result);
    }

    @PostMapping("save-main-photo")
    public R<Boolean> saveMainPhoto(@RequestParam("photoId") Integer photoId, @SessionAttribute Long userId) {
        boolean set = httpUserMainPhotoService.set(userId, photoId);
        return ResUtils.ok(set);
    }

    @PostMapping("save-primary-photo")
    public ResponseEntity<Boolean> savePrimaryPhoto(@RequestBody List<OrderAndPhotoId> orderAndPhotoIds, @RequestParam Integer mainPhotoId, @RequestParam Integer max, @RequestParam Integer introduceTypeEnum, @SessionAttribute Long userId) {
        if (mainPhotoId == null) {
            String msg = String.format("头像照片不存在，用户ID：%s。", userId);
            log.error(msg);
            return ResponseEntity.internalServerError().header("msg", msg).build();
        }
        httpUserMainPhotoService.set(userId, mainPhotoId);
        if (CollUtils.isNotEmpty(orderAndPhotoIds)) {
            // 按顺序存入，取出即有序
            httpUserIntroducePhotoService.saveAndReturnId(orderAndPhotoIds, max, IntroduceEnumUtils.indexToIntroduceTypeEnum(introduceTypeEnum), userId);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("self-user-id-to-tag-id")
    public R<List<Integer>> selfUserIdToTagId(@SessionAttribute Long userId) {

        List<Integer> result = httpTagService.userIdToTagId(Collections.singleton(userId)).get(userId);
        return ResUtils.ok(result);
    }
}
