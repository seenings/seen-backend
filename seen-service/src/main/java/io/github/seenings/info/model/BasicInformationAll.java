package io.github.seenings.info.model;

/**
 * BasicInformationAll
 *
 * @author chixuehui
 * @since 2023-02-11
 *
 * @param basicInformation  基本信息
 * @param educationAndWork  教育与工作
 * @param contactInformation    联系信息
 */
public record BasicInformationAll(
        BasicInformation basicInformation, EducationAndWork educationAndWork, ContactInformation contactInformation) {}
