package com.songchi.seen.voice.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.sys.config.SeenConfig;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.voice.entity.Voice;
import com.songchi.seen.voice.service.IVoiceService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.RandomUtil;
import io.gitee.seen.core.util.DateUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 语音 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Slf4j
@RestController
@RequestMapping(PublicConstant.REST + "voice/voice")
public class VoiceController {
    @Resource
    IVoiceService iVoiceService;

    @Resource
    private SeenConfig seenConfig;

    @PostMapping("upload")
    public R<Integer> upload(MultipartFile file, @SessionAttribute(PublicConstant.USER_ID) Integer userId) {
        if (file == null || file.isEmpty()) {
            String msg = "文件为空。";
            log.error(msg);
            return ResUtils.error(msg);
        }

        String originalFilename = URLDecoder.decode(file.getOriginalFilename(), StandardCharsets.UTF_8);
        String relativePath = DateUtils.getBasicIsoDate() + File.separator + DateUtils.formatTime() + "-" +
                RandomUtil.randomNumbers(4) + "-" + originalFilename;
        String realPath = seenConfig.getPathConfig().getVoicePath() + relativePath;
        try {
            FileUtil.writeFromStream(file.getInputStream(), Paths.get(realPath).toFile());
        } catch (IOException e) {
            log.error("", e);
            return ResUtils.error(e.getMessage());
        }
        Integer pathId = iVoiceService.setPath(relativePath, userId);
        return ResUtils.ok(pathId);
    }

    @PostMapping
    public R<Voice> post(@RequestBody Voice entity) {
        iVoiceService.save(entity);
        return ResUtils.ok(entity);
    }

    @PutMapping
    public R<Voice> put(@RequestBody Voice entity) {
        iVoiceService.updateById(entity);
        return ResUtils.ok(entity);
    }

    @GetMapping("{id}")
    public R<Voice> get(@PathVariable Serializable id) {
        return ResUtils.ok(iVoiceService.getById(id));
    }

    /**
     * 根据声音ID获取相对url
     *
     * @param voiceIds 声音ID
     * @return 声音ID对应相对url
     */
    @PostMapping("voice-id-to-url")
    public R<Map<Integer, String>> voiceIdToUrl(@RequestBody Set<Integer> voiceIds) {
        return ResUtils.ok(iVoiceService.voiceIdToUrl(voiceIds));
    }

    @DeleteMapping("{id}")
    public R<Voice> del(@PathVariable Serializable id) {
        Voice entity = new Voice();
        entity.setId((Integer) id);
        entity.setDeleted(1);
        boolean b = iVoiceService.updateById(entity);
        return b ? ResUtils.ok(iVoiceService.getById(id)) : ResUtils.error(entity, "删除语音失败");
    }
}
