package io.github.seenings.qr.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

@Slf4j
class QrShareServiceImplTest {

    @Test
    void originUrlToStream() {

        String path = System.getProperty("user.home") + "/run/test/qr.svg";
        String originUrl = "http://www.baidu.com";
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ByteArrayInputStream inputStream = IoUtil.toStream(out);
        try {
            if (!new File(path).getParentFile().mkdirs()) {
                log.warn("目录已存在");
            }
            if (!new File(path).createNewFile()) {
                log.warn("文件已存在");
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            QrCodeUtil.generate(originUrl, 300, 300, QrCodeUtil.QR_TYPE_SVG, out);
            ByteArrayInputStream inputStream = IoUtil.toStream(out);
            FileUtil.writeFromStream(inputStream, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        FileUtil.writeFromStream(inputStream, new File("~/run/test/qr.svg"));

        Assertions.assertTrue(FileUtil.exist(path));
    }
}