package com.songchi.seen.file.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ImageCompressServiceImplTest
 *
 * @author chixuehui
 * @since 2023-12-12
 */
class ImageCompressServiceImplTest {


//        @Test
    void test() {
        // 读取原始图片
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(
                    "/Users/chixuehui/seen/photo/20230604/20230604113747821-8625-8f14647c1afd6631805c8e6c506c40e8.jpeg"));
            // 创建目标图片，设置压缩后的宽度和高度
            int width = originalImage.getWidth() / 2;
            int height = originalImage.getHeight() / 2;
            BufferedImage compressedImage = new BufferedImage(width, height, originalImage.getType());
            // 将原始图片绘制到目标图片上，实现压缩
            compressedImage.getGraphics().drawImage(originalImage, 0, 0, width, height, null);
            // 保存压缩后的图片
            File outputFile = new File("/Users/chixuehui/Downloads/"+"compressed.jpg");
            ImageIO.write(compressedImage, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}