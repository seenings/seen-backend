package io.github.seenings.photo.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.photo.po.PhotoPO;
import io.github.seenings.photo.service.PhotoPOService;
import io.github.seenings.sys.util.ListUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 照片
 */
@Mapper
interface PhotoPOMapper extends BaseMapper<PhotoPO> {
}

/**
 * 照片
 */
@Service
public class PhotoPOServiceImpl extends ServiceImpl<PhotoPOMapper, PhotoPO> implements PhotoPOService {

    /**
     * 根据照片ID获取文件ID
     *
     * @param ids 照片ID
     * @return 照片ID对应文件ID
     */
    @Override
    public Map<Integer, Integer> idToFileId(Set<Integer> ids) {

        return ListUtil.partition(ListUtils.valueIsNull(ids), 100).parallelStream()
                .flatMap(subs -> list(new LambdaQueryWrapper<PhotoPO>().in(PhotoPO::getId, subs)
                        .select(PhotoPO::getId, PhotoPO::getFileId)).stream()).collect(Collectors.toMap(PhotoPO::getId, PhotoPO::getFileId));
    }


    /**
     * 设置照片
     *
     * @param fileId 文件ID
     * @return 照片ID
     */
    @Override
    public Integer set(Integer fileId) {
        PhotoPO entity = new PhotoPO();
        entity.setFileId(fileId);
        save(entity);
        return entity.getId();
    }
}
