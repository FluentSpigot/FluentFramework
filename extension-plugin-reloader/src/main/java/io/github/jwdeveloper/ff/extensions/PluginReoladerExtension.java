package io.github.jwdeveloper.ff.extensions;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public class PluginReoladerExtension implements FluentApiExtension
{
    private CancellationToken ctx;

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder)
    {
        ctx = new CancellationToken();
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var service = new FileTrackerService(fluentAPI.tasks());
        service.startAsync(ctx);
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI)  {
        FluentLogger.LOGGER.info("DUABLIGN");
        ctx.cancel();
    }
}
