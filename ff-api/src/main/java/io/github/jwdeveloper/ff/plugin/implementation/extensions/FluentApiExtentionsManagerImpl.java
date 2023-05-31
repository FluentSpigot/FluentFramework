package io.github.jwdeveloper.ff.plugin.implementation.extensions;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionModel;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtensionsManager;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import lombok.Getter;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FluentApiExtentionsManagerImpl implements FluentApiExtensionsManager {
    private final Collection<ExtensionModel> extensionsModels;
    private final PluginLogger logger;

    @Getter
    private final EventGroup<FluentApiExtension> beforeEachOnConfigure;
    @Getter
    private final EventGroup<FluentApiSpigotBuilder> afterOnConfigure;
    @Getter
    private final EventGroup<FluentApiSpigot> beforeOnEnable;
    @Getter
    private final EventGroup<FluentApiSpigot> afterOnEnable;
    @Getter
    private final EventGroup<FluentApiExtension> beforeEachOnEnable;
    @Getter
    private final EventGroup<FluentApiExtension> beforeEachDisable;

    @Getter
    private final EventGroup<FluentApiSpigot> afterOnDisable;


    public FluentApiExtentionsManagerImpl(PluginLogger logger)
    {
        this.logger = logger;
        extensionsModels = new ConcurrentLinkedDeque<>();
        beforeEachOnConfigure = new EventGroup<>();
        beforeOnEnable = new EventGroup<>();
        beforeEachOnEnable = new EventGroup<>();
        beforeEachDisable = new EventGroup<>();
        afterOnConfigure = new EventGroup<>();
        afterOnEnable = new EventGroup<>();
        afterOnDisable = new EventGroup<>();
    }


    @Override
    public void register(FluentApiExtension extension) {
        register(extension, extension.getPriority());
    }

    @Override
    public void register(FluentApiExtension extention, ExtentionPriority piority) {
        extensionsModels.add(new ExtensionModel(extention,piority));
    }

    @Override
    public void registerLow(FluentApiExtension extention) {
        register(extention, ExtentionPriority.LOW);
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        //FluentLogger.LOGGER.success("onConfiguration");
        for(var extention : extensionsModels)
        {
           // FluentLogger.LOGGER.log("Piority",extention.getPiority().name(),extention.getExtention().getClass().getSimpleName());
            beforeEachOnConfigure.invoke(extention.getExtension());
            extention.getExtension().onConfiguration(builder);
        }
        afterOnConfigure.invoke(builder);
    }

    @Override
    public void onEnable(FluentApiSpigot fluentAPI) {
        try
        {
            beforeOnEnable.invoke(fluentAPI);
            var sorted = sortByPiority();
            for(var extension : sorted)
            {
                beforeEachOnEnable.invoke(extension.getExtension());
                extension.getExtension().onFluentApiEnable(fluentAPI);
            }
            afterOnEnable.invoke(fluentAPI);
        }
        catch (Exception e)
        {
            logger.error("FluentApiExtension onEnable error",e);
            throw new RuntimeException("FluentApiExtension onEnable error",e);
        }
    }

    @Override
    public void onDisable(FluentApiSpigot fluentAPI) {
        var sorted = sortByPiority();
        for(var extention : sorted)
        {
            try
            {
                beforeEachDisable.invoke(extention.getExtension());
                extention.getExtension().onFluentApiDisabled(fluentAPI);
            }
            catch (Exception e)
            {
               logger.error("FluentApiExtension onDisable error",e);
            }
        }
        afterOnDisable.invoke(fluentAPI);
    }


    private List<ExtensionModel> sortByPiority()
    {
        return extensionsModels.stream().toList().stream().sorted(Comparator.comparing(item -> item.getPriority().getLevel())).toList();
    }
}
