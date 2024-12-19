package com.songchi.seen.extra.util;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileUtils {

    /**
     * 新建文件，且新建好父级目录
     *
     * @param path 路径
     * @return 文件
     */
    public static File newFile(String path) {
        File destFile = new File(path);
        if (!destFile.getParentFile().mkdir()) {
            String msg = "文件夹创建失败。" + destFile.getParent();
            log.warn(msg);
        }
        return destFile;
    }

    public static void writeFile(byte[] fileBytes, String realPath) {
        File destFile = new File(realPath);
        if (!destFile.getParentFile().mkdir()) {
            String msg = "文件夹创建失败。" + destFile.getParent();
            log.error(msg);
        }
        FileUtil.writeBytes(fileBytes, destFile);
    }
}
