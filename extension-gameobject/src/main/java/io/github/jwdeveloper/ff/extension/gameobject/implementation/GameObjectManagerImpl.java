package io.github.jwdeveloper.ff.extension.gameobject.implementation;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.extension.gameobject.api.GameComponent;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.extension.gameobject.api.GameObjectManager;
import org.bukkit.Location;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GameObjectManagerImpl extends EventBase implements GameObjectManager {
    private final List<GameComponent> gameObjects = new ArrayList();
    private final PluginLogger logger;

    public GameObjectManagerImpl(Plugin plugin, PluginLogger logger) {
        super(plugin);
        this.logger = logger;
    }

    public boolean register(GameComponent gameObject, Location location) {
        if (gameObjects.contains(gameObject)) {
            return false;
        }
        gameObjects.add(gameObject);
        try {
            gameObject.create(location.clone());
            return true;
        } catch (Exception e) {
            logger.error("unable to create Gameobject" + gameObject.getClass().getSimpleName(), e);
        }
        return false;
    }

    public void unregister(GameComponent gameObject) {
        if (!gameObjects.contains(gameObject)) {
            return;
        }
        gameObjects.remove(gameObject);
        try {
            gameObject.destroy();
        } catch (Exception e) {
            logger.error("unable to destroy Gameobject" + gameObject.getClass().getSimpleName(), e);
        }
    }


    @Override
    public void onPluginStop(PluginDisableEvent event) {
        for (var gameObject : gameObjects) {
            try {
                gameObject.destroy();

            } catch (Exception e) {
                logger.error("unable to destroy gameobject" + gameObject.getClass().getSimpleName(), e);
            }
        }
    }
}
