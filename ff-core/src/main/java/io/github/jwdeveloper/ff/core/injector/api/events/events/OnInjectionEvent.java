package io.github.jwdeveloper.ff.core.injector.api.events.events;

import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.core.injector.api.models.InjectionInfo;

import java.util.Map;

public record OnInjectionEvent(Class<?> input, InjectionInfo injectionInfo, Object result, Map<Class<?>, InjectionInfo> injectionInfoMap, Container container)
{

}
