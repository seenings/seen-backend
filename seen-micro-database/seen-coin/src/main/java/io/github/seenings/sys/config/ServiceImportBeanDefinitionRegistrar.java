package io.github.seenings.sys.config;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * Service注解配置
 */
public class ServiceImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * 注册Bean
     * @param importingClassMetadata    导入元数据
     * @param registry  注册器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(Repository.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
        scanner.scan("io.github.seenings"); // 指定扫描的包路径
    }
}