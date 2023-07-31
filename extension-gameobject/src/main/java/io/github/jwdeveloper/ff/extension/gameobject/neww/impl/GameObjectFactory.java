package io.github.jwdeveloper.ff.extension.gameobject.neww.impl;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.Component;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.GameObject;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameObjectImpl;
import org.bukkit.Location;
import org.bukkit.World;

public class GameObjectFactory
{

    public static GameObject instance(Location location,  Class<? extends Component> ... components)
    {
        return instance(StringUtils.EMPTY, location, components);
    }

    public static GameObject instance(String name, Location location,  Class<? extends Component> ... components)
    {
        var gameObject = new GameObjectImpl();
        gameObject.meta().setName(name);
        gameObject.transform().position(location.getX(),location.getY(),location.getZ());
        gameObject.transform().setWorld(location.getWorld());
        for(var component : components)
        {
            gameObject.addComponent(component);
        }
        return gameObject;
    }

    public static GameObject instance(String name, GameObject parent, Class<? extends Component> ... components)
    {
        var gameObject = new GameObjectImpl();
        gameObject.meta().setName(name);
        gameObject.transform().setWorld(parent.transform().world());
        for(var component : components)
        {
            gameObject.addComponent(component);
        }
        gameObject.setParent(parent);
        return gameObject;
    }
}
