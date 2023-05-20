package io.github.jwdeveloper.ff.core.injector.api.containers;

import io.github.jwdeveloper.ff.core.injector.api.models.RegistrationInfo;

public interface Container extends Cloneable
{
     boolean register(RegistrationInfo registrationInfo) throws Exception;

     Object find(Class<?> type);
}