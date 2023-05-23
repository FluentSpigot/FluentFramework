package io.github.jwdeveloper.ff.core.injector.implementation.containers;

import io.github.jwdeveloper.ff.core.common.logger.BukkitLogger;
import io.github.jwdeveloper.ff.core.injector.api.containers.FluentContainer;
import io.github.jwdeveloper.ff.core.injector.api.events.EventHandler;
import io.github.jwdeveloper.ff.core.injector.api.factory.InjectionInfoFactory;
import io.github.jwdeveloper.ff.core.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.ff.core.injector.api.provider.InstanceProvider;
import io.github.jwdeveloper.ff.core.injector.api.search.SearchAgent;

import java.util.List;

public class FluentContainerImpl extends DefaultContainer implements FluentContainer {

    public FluentContainerImpl(
            SearchAgent searchAgent,
            InstanceProvider instaneProvider,
            EventHandler eventHandler,
            BukkitLogger logger,
            InjectionInfoFactory injectionInfoFactory,
            List<RegistrationInfo> registrationInfos) {
        super(searchAgent, instaneProvider, eventHandler, logger, injectionInfoFactory, registrationInfos);
    }




}
