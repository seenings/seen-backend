package io.github.seenings.sys.config;

import io.github.seenings.photo.http.HttpPhotoService;
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
 * 文件的配置
 */
@Slf4j
@AutoConfiguration
@AllArgsConstructor
public class FileAutoConfig {
    /**
     * 文件服务的代理
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenFile;

    /**
     * 照片
     *
     * @return 接口实例
     */
    @Bean
    public HttpPhotoService httpPhotoService() {
        return httpServiceProxyFactoryBySeenFile.createClient(HttpPhotoService.class);
    }
}