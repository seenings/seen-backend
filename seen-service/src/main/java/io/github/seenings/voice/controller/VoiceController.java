package io.github.seenings.voice.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import io.github.seenings.time.component.NowComponent;
import lombok.AllArgsConstructor;
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

import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.sys.config.SeenConfig;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.voice.entity.Voice;
import io.github.seenings.voice.service.IVoiceService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 语音 前端控制器
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.REST + "voice/voice")
public class VoiceController {
    private IVoiceService iVoiceService;

    private SeenConfig seenConfig;
    /**
     * 当前时间组件
     */
    private NowComponent nowComponent;

    @PostMapping("upload")
    public R<Integer> upload(MultipartFile file, @SessionAttribute(PublicConstant.USER_ID) Long userId) {
        if (file == null || file.isEmpty()) {
            String msg = "文件为空。";
            log.error(msg);
            return ResUtils.error(msg);
        }

        String originalFilename = URLDecoder.decode(file.getOriginalFilename(), StandardCharsets.UTF_8);
        String relativePath = nowComponent.nowToBasicIsoDate() + File.separator + nowComponent.nowToSeventeenFormatDate() + "-" + RandomUtil.randomNumbers(4) + "-" + originalFilename;
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
