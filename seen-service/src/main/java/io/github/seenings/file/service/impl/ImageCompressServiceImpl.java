package io.github.seenings.file.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import io.github.seenings.file.service.ImageCompressService;

import lombok.extern.slf4j.Slf4j;

/**
 * ImageCompressServiceImpl
 *
 * @author chixuehui
 * @since 2023-12-10
 */
@Slf4j
@Service
public class ImageCompressServiceImpl implements ImageCompressService {

    /**
     * 压缩
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @throws IOException 异常
     */
    @Override
    public boolean compress(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        // 不可压缩
        if (bufferedImage == null) {
            return false;
        }
        // 压缩比例
        int rate = bufferedImage.getWidth() / 100;
        // 创建目标图片，设置压缩后的宽度和高度
        int width = bufferedImage.getWidth() / rate;
        int height = bufferedImage.getHeight() / rate;
        BufferedImage compressedImage = new BufferedImage(width, height, bufferedImage.getType());
        // 将原始图片绘制到目标图片上，实现压缩
        boolean drawImage = compressedImage.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null);
        if (drawImage) {
            log.info("重新绘图成功,宽度:{},高度:{}", width, height);
            // 写库
            return ImageIO.write(compressedImage, "jpg", outputStream);
        } else {
            return false;
        }
    }
}
