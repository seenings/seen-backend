package io.github.seenings.qr.controller;

import io.github.seenings.sys.constant.PublicConstant;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * 二维码分享
 */
@HttpExchange(PublicConstant.REST + "/qr/qr-share")
public interface QrShareController {
    /**
     * 根据地址获取二维码图片
     *
     * @param originUrl 地址
     * @return 二维码图片
     */
    @GetExchange("origin-url-to-stream")
    ResponseEntity<InputStreamResource> originUrlToStream(@RequestParam("originUrl") String originUrl);
}
