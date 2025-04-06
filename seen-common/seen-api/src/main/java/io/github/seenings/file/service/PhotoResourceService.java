package io.github.seenings.file.service;

import java.io.InputStream;

/**
 * 照片资源
 */
public interface PhotoResourceService {
    InputStream photoIdToInputStream(Integer photoId);
}
