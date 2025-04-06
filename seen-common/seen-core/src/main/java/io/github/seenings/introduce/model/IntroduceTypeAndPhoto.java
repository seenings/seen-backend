package io.github.seenings.introduce.model;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;

/**
 * 介绍照片
 */
public record IntroduceTypeAndPhoto(IntroduceTypeEnum introduceTypeEnum, Integer order, Integer photoId) {
}
