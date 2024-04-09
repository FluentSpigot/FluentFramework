package io.github.jwdeveloper.ff.extension.gameobject.neww;

import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.Component;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.GameObjectEngine;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
public class GameObjectExtension implements FluentApiExtension
{
    //EMISSION : If the shader supports PBR for particles maybe, if not then no
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder)
    {


        builder.container().registerSingleton(GameObjectEngine.class);
    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        fluentAPI.container().findInjection(GameObjectEngine.class).runAsync();
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception
    {
        fluentAPI.container().findInjection(GameObjectEngine.class).close();
    }
}
