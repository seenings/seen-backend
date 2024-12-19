package com.songchi.seen.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ImageCompressService
 *
 * @author chixuehui
 * @since 2023-12-10
 */
public interface ImageCompressService {
    /**
     * 压缩
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @throws IOException 异常
     */
    boolean compress(InputStream inputStream, OutputStream outputStream) throws IOException;
}
