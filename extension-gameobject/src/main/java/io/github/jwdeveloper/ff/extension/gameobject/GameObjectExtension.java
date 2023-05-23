package io.github.jwdeveloper.ff.extension.gameobject;

import io.github.jwdeveloper.ff.extension.gameobject.api.GameObjectManager;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.GameObjectManagerImpl;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class GameObjectExtension implements FluentApiExtension
{
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder)
    {
        var manager = new GameObjectManagerImpl(builder.plugin(), builder.logger());
        builder.container().registerSigleton(GameObjectManager.class, manager);
    }
}
