package io.github.jwdeveloper.ff.plugin.implementation.extensions.container;

import io.github.jwdeveloper.dependance.injector.api.annotations.IgnoreInjection;
import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.containers.ContainerConfiguration;
import io.github.jwdeveloper.dependance.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.dependance.api.JarScanner;
import io.github.jwdeveloper.ff.plugin.api.FluentApiContainerBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.implementation.FluentPlayerContext;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.implementation.FluentPlayerContextListener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class FluentInjectionFactory {
    public record Result(FluentInjectionImpl fluentInjection, List<Class<?>> toInitializeTypes){}
    private final Plugin plugin;
    private final FluentApiContainerBuilder builder;
    private final JarScanner typeManager;
    private final PluginLogger logger;
    private final List<Class<?>> toInitializeTypes;

    public List<RegistrationInfo> playerScopedRegistrations;


    public FluentInjectionFactory(FluentApiContainerBuilder builder,
                                  PluginLogger logger,
                                  Plugin plugin,
                                  JarScanner classTypesManager)
    {
        this.builder = builder;
        this.typeManager = classTypesManager;
        this.logger = logger;
        this.plugin = plugin;

        toInitializeTypes = new ArrayList<>();
    }

    public Result create() throws Exception {

      /*  var containerConfig = builder.getConfiguration();
        if(containerConfig.isEnableAutoScan())
        {
            autoScan(containerConfig);
        }
        builder.getConfiguration().onRegistration(onRegistrationEvent ->
        {
            if(onRegistrationEvent.injectionInfo().getLifeTime() != LifeTime.PLAYER_SCOPE)
            {
                return true;
            }
            playerScopedRegistrations.add(onRegistrationEvent.registrationInfo());
            return false;
        });

        var container = builder.build();
        var playerContext = createPlayerScopeContainer(container);
        var fluentInjection = new FluentInjectionImpl(container, playerContext);
        return new Result(fluentInjection, toInitializeTypes);*/
        return null;
    }

/*
    public FluentPlayerContext createPlayerScopeContainer(FluentContainer fluentContainer)
    {
        var listener = new FluentPlayerContextListener(plugin);
        return new FluentPlayerContext(fluentContainer, playerScopedRegistrations, listener,logger);
    }

    private void autoScan(ContainerConfiguration configuration)
    {
        var registeredClasses = configuration.getRegisterdTypes();
        var injectionsInfo = typeManager.findByAnnotation(Injection.class);
        for (var _class : injectionsInfo) {
            if (registeredClasses.contains(_class) ||
                    _class.isAnnotationPresent(IgnoreInjection.class) ||
                    _class.isInterface()) {
                continue;
            }

            registerType(_class);
        }
    }
*/

    private void registerType(Class<?> _class) {
  /*      var injectionInfo = _class.getAnnotation(Injection.class);

        if (!injectionInfo.lazyLoad())
            toInitializeTypes.add(_class);

        var interfaces = _class.getInterfaces();
        if (interfaces.length == 0 || injectionInfo.ignoreInterface()) {
            builder.register(_class, injectionInfo.lifeTime());
            return;
        }

        if (injectionInfo.toInterface().equals(Object.class)) {
            builder.register((Class) interfaces[0], _class, injectionInfo.lifeTime());
            return;
        }

        builder.register((Class) injectionInfo.toInterface(), _class, injectionInfo.lifeTime());*/
    }


}
