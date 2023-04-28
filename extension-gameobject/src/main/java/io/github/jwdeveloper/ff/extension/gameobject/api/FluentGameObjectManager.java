package io.github.jwdeveloper.ff.extension.gameobject.api;

import io.github.jwdeveloper.ff.extension.gameobject.implementation.GameObject;
import org.bukkit.Location;

public interface FluentGameObjectManager
{
    boolean register(GameObject gameObject, Location location);

    void unregister(GameObject gameObject);
}
