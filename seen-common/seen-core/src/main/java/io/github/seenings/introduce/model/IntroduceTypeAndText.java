package io.github.seenings.introduce.model;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;

/**
 * IntroduceTypeAndText
 *
 * @author chixuehui
 * @since 2022-11-27
 */
public record IntroduceTypeAndText(IntroduceTypeEnum introduceTypeEnum, Integer textId) {}
