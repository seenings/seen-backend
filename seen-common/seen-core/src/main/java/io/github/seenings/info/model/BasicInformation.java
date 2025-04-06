package io.github.seenings.info.model;

import java.time.LocalDate;
import java.util.List;

/**
 * BasicInformation
 *
 * @author chixuehui
 * @since 2023-02-11
 * @param aliasName 别名
 * @param birthDate 出生年月日
 * @param sex   性别
 * @param stature   身高
 * @param weight    体重
 * @param currentResidence  现居地
 * @param birthPlace    出生地
 * @param maritalStatus 婚姻状况
 */
public record BasicInformation(
        String aliasName,
        LocalDate birthDate,
        Integer sex,
        Integer stature,
        Integer weight,
        List<String> currentResidence,
        List<String> birthPlace,
        Integer maritalStatus) {}
