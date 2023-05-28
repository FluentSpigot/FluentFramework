package io.github.jwdeveloper.ff.core.injector.implementation.provider;

import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.injector.api.models.InjectionInfo;
import io.github.jwdeveloper.ff.core.injector.api.provider.InstanceProvider;
import io.github.jwdeveloper.ff.core.injector.implementation.utilites.Messages;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class InstanceProviderImpl implements InstanceProvider
{
    @Override
    public Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Container container) throws Exception {
        if (info.getLifeTime() == LifeTime.SINGLETON && info.getInstnace() != null)
            return info.getInstnace();

        Object result = null;
        InjectionInfo handler = null;
        Class<?> parameterClass = null;
        if (info.hasInjectedConstructor()) {
            var i = 0;
            for (var parameterType : info.getConstructorTypes())
            {
                parameterClass = parameterType;
                info.getConstructorPayLoadTemp()[i] = container.find(parameterClass, info.getInjectedConstructor().getGenericParameterTypes()[i]);
                i++;
            }
            result = info.getInjectedConstructor().newInstance(info.getConstructorPayLoadTemp());
            info.setInstnace(result);
            return result;
        }

        result = switch (info.getRegistrationInfo().registrationType())
        {
            case InterfaceAndIml, OnlyImpl -> info.getRegistrationInfo().implementation().newInstance();
            case InterfaceAndProvider, List -> info.getRegistrationInfo().provider().apply(container);
        };
        info.setInstnace(result);
        return result;
    }
}
