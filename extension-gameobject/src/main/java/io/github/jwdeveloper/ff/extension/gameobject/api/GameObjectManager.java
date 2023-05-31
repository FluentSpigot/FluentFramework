package io.github.jwdeveloper.ff.extension.gameobject.api;

import io.github.jwdeveloper.ff.extension.gameobject.implementation.GameObject;
import org.bukkit.Location;

public interface GameObjectManager
{
    boolean register(GameComponent gameObject, Location location);

    void unregister(GameComponent gameObject);
}
