package io.github.seenings.text.service;

import java.util.Map;
import java.util.Set;

/**
 * TextService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface TextService {
    Integer saveAndReturnId(String text);

    Map<Integer, String> textIdToText(Set<Integer> textIds);
}
