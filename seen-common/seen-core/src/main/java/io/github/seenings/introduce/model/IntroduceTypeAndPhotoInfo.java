package io.github.seenings.introduce.model;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;

/**
 * 照片介绍信息
 */
public record IntroduceTypeAndPhotoInfo(IntroduceTypeEnum introduceTypeEnum, Integer order, Integer id) {
}
