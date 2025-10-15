package io.github.seenings.qr.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import io.github.seenings.qr.controller.QrShareController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Duration;

/**
 * 二维码分享
 */
@RestController
public class QrShareServiceImpl implements QrShareController {
    /**
     * 根据地址获取二维码图片
     *
     * @param originUrl 地址
     * @return 二维码图片
     */
    @Override
    public ResponseEntity<InputStreamResource> originUrlToStream(String originUrl) {

        // 生成指定url对应的二维码到文件，宽和高都是300像素
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        QrCodeUtil.generate(originUrl, 300, 300, QrCodeUtil.QR_TYPE_SVG, out);
        ByteArrayInputStream inputStream = IoUtil.toStream(out);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofDays(30L))).body(new InputStreamResource(inputStream));
    }
}
