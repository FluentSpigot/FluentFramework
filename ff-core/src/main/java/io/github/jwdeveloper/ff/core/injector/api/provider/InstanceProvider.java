package io.github.jwdeveloper.ff.core.injector.api.provider;

import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.core.injector.api.models.InjectionInfo;

import java.util.Map;

public interface InstanceProvider
{
     Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Container container) throws Exception;
}
