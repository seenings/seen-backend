package io.github.seenings.info.model;

import java.util.List;

/**
 * EducationAndWork
 *
 * @param highestEducation  最高学历
 * @param highestSchoolIds  毕业院校
 * @param workPositionId    职位ID
 * @param workCompany       公司
 * @param annualIncomeIndex 年收入
 */
public record EducationAndWork(Integer highestEducation, List<String> highestSchoolIds, Integer workPositionId,
                               String workCompany, Integer annualIncomeIndex) {
}
