package io.github.jwdeveloper.ff.extensions;

import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public class PluginReoladerExtension implements FluentApiExtension
{
    private  CancelationToken ctx;

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder)
    {
        ctx = new CancelationToken();
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
