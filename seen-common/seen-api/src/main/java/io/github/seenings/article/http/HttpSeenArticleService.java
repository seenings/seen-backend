package io.github.seenings.article.http;

import org.springframework.cloud.openfeign.FeignClient;

import io.github.seenings.sys.constant.ServiceNameConstant;


/**
 * HttpSeenArticleService
 *
 * @author chixuehui
 * @since 2022-11-27
 */
@FeignClient(name = ServiceNameConstant.SERVICE_SEEN_ARTICLE, contextId = "HttpSeenArticleService")
public interface HttpSeenArticleService {}
