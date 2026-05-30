package io.github.seenings.sys.config;

import io.github.seenings.info.http.*;
import io.github.seenings.school.http.HttpEducationalService;
import io.github.seenings.school.http.HttpSchoolGraduateService;
import io.github.seenings.school.http.HttpSchoolService;
import io.github.seenings.school.http.HttpStudentInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 学校的配置
 */
@Slf4j
@AutoConfiguration
@AllArgsConstructor
public class SchoolAutoConfig {
    /**
     * 学校服务的代理
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenSchool;

    /**
     * 学生信息
     *
     * @return 接口实例
     */
    @Bean
    public HttpStudentInfoService httpStudentInfoService() {
        return httpServiceProxyFactoryBySeenSchool.createClient(HttpStudentInfoService.class);
    }
    /**
     * 学历
     *
     * @return 接口实例
     */
    @Bean
    public HttpEducationalService httpEducationalService() {
        return httpServiceProxyFactoryBySeenSchool.createClient(HttpEducationalService.class);
    }
    /**
     * 学位
     *
     * @return 接口实例
     */
    @Bean
    public HttpSchoolGraduateService httpSchoolGraduateService() {
        return httpServiceProxyFactoryBySeenSchool.createClient(HttpSchoolGraduateService.class);
    }
    /**
     * 学校
     *
     * @return 学校
     */
    @Bean
    public HttpSchoolService httpSchoolService() {
        return httpServiceProxyFactoryBySeenSchool.createClient(HttpSchoolService.class);
    }
}