package com.songchi.seen.photo.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.photo.po.MainPhotoPO;
import com.songchi.seen.photo.service.MainPhotoPOService;
import com.songchi.seen.sys.util.ListUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 照片
 */
@Mapper
interface MainPhotoPOMapper extends BaseMapper<MainPhotoPO> {
}

/**
 * 照片
 */
@Service
public class MainPhotoPOServiceImpl extends ServiceImpl<MainPhotoPOMapper, MainPhotoPO> implements MainPhotoPOService {

    /**
     * 根据照片ID获取文件ID
     *
     * @param ids 照片ID
     * @return 照片ID对应文件ID
     */
    @Override
    public Map<Integer, Integer> idToFileId(Set<Integer> ids) {

        return ListUtil.partition(ListUtils.valueIsNull(ids), 100).parallelStream().flatMap(subs -> list(new LambdaQueryWrapper<MainPhotoPO>().in(MainPhotoPO::getId, subs).select(MainPhotoPO::getId, MainPhotoPO::getFileId)).stream()).collect(Collectors.toMap(MainPhotoPO::getId, MainPhotoPO::getFileId));
    }


    /**
     * 设置照片
     *
     * @param fileId 文件ID
     * @return 照片ID
     */
    @Override
    public Integer set(Integer fileId) {
        MainPhotoPO entity = new MainPhotoPO();
        entity.setFileId(fileId);
        save(entity);
        return entity.getId();
    }
}
