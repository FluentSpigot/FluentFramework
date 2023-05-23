package io.github.jwdeveloper.ff.plugin.implementation.extensions;

import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionModel;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtensionsManager;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.core.common.logger.BukkitLogger;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FluentApiExtentionsManagerImpl implements FluentApiExtensionsManager {
    private final Collection<ExtensionModel> extensions;
    private final BukkitLogger logger;

    public FluentApiExtentionsManagerImpl(BukkitLogger logger)
    {
        this.logger = logger;
        extensions = new ConcurrentLinkedDeque<>();
    }


    @Override
    public void register(FluentApiExtension extension) {
        register(extension, extension.getPriority());
    }

    @Override
    public void register(FluentApiExtension extention, ExtentionPriority piority) {
        extensions.add(new ExtensionModel(extention,piority));
    }

    @Override
    public void registerLow(FluentApiExtension extention) {
        register(extention, ExtentionPriority.LOW);
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        //FluentLogger.LOGGER.success("onConfiguration");
        for(var extention : extensions)
        {
           // FluentLogger.LOGGER.log("Piority",extention.getPiority().name(),extention.getExtention().getClass().getSimpleName());
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

                extension.getExtension().onFluentApiEnable(fluentAPI);
            }
        }
        catch (Exception e)
        {
            logger.error("onFluentApiEnable Fluent API Extension exception",e);
        }
    }

    @Override
    public void onDisable(FluentApiSpigot fluentAPI) {
        var sorted = sortByPiority();
        for(var extention : sorted)
        {
            try
            {
                extention.getExtension().onFluentApiDisabled(fluentAPI);
            }
            catch (Exception e)
            {
               logger.error("disable error",e);
            }
        }
    }



    private List<ExtensionModel> sortByPiority()
    {
        return extensions.stream().toList().stream().sorted(Comparator.comparing(item -> item.getPriority().getLevel())).toList();
    }
}
