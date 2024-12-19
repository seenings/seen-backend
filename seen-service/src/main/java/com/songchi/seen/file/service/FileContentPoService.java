package com.songchi.seen.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songchi.seen.file.entity.FileContentPo;
import com.songchi.seen.file.model.FileContent;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface FileContentPoService extends IService<FileContentPo> {

    Map<Integer, FileContent> fileIdToFileContent(Set<Integer> fileIds);

    Integer insert(FileContent fileContent);
}
