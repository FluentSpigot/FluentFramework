package io.github.jwdeveloper.ff.extension.gameobject;

import io.github.jwdeveloper.ff.extension.gameobject.neww.GameObjectExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class GameObjectEngineApi
{
    public static FluentApiExtension use()
    {
        return new GameObjectExtension();
    }
}
