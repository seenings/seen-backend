package io.github.seenings.zone.service;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public interface ITagParentService   {

    Map<String, Integer> tagParentNameToTagParentId(Collection<String> tagParentNames);
}
