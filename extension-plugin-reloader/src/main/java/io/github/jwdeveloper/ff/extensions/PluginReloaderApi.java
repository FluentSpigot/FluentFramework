package io.github.jwdeveloper.ff.extensions;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class PluginReloaderApi
{
    public static FluentApiExtension use()
    {
        return new PluginReoladerExtension();
    }
}
