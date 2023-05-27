package io.github.jwdeveloper.ff.plugin.implementation.extensions;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventsGroupCancelable;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionModel;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtensionsManager;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.core.common.logger.BukkitLogger;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FluentApiExtentionsManagerImpl implements FluentApiExtensionsManager {
    private final Collection<ExtensionModel> extensionsModels;
    private final BukkitLogger logger;


    @Getter
    private final EventGroup<FluentApiExtension> beforeOnConfigure;
    @Getter
    private final EventGroup<FluentApiExtension> beforeEnable;
    @Getter
    private final EventGroup<FluentApiExtension> beforeDisable;


    public FluentApiExtentionsManagerImpl(BukkitLogger logger)
    {
        this.logger = logger;
        extensionsModels = new ConcurrentLinkedDeque<>();
        beforeOnConfigure= new EventGroup<>();
        beforeEnable = new EventGroup<>();
        beforeDisable = new EventGroup<>();
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
            beforeOnConfigure.invoke(extention.getExtension());
            extention.getExtension().onConfiguration(builder);
        }
    }

    @Override
    public void onEnable(FluentApiSpigot fluentAPI) {
        try
        {
            var sorted = sortByPiority();
            for(var extension : sorted)
            {
                beforeEnable.invoke(extension.getExtension());
                extension.getExtension().onFluentApiEnable(fluentAPI);
            }
        }
        catch (Exception e)
        {
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
                beforeDisable.invoke(extention.getExtension());
                extention.getExtension().onFluentApiDisabled(fluentAPI);
            }
            catch (Exception e)
            {
               logger.error("FluentApiExtension onDisable error",e);
            }
        }
    }


    private List<ExtensionModel> sortByPiority()
    {
        return extensionsModels.stream().toList().stream().sorted(Comparator.comparing(item -> item.getPriority().getLevel())).toList();
    }
}
