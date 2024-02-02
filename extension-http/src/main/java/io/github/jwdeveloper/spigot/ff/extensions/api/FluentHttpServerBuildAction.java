package io.github.jwdeveloper.spigot.ff.extensions.api;

import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;

public interface FluentHttpServerBuildAction
{
    public void onBuild(FluentApiSpigotBuilder fluentApiSpigotBuilder, FluentHttpServerBuilder httpServerBuilder);


}
