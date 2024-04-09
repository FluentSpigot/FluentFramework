package io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player;

import io.github.jwdeveloper.dependance.Dependance;
import io.github.jwdeveloper.dependance.injector.api.containers.Container;
import io.github.jwdeveloper.dependance.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.ff.core.cache.api.PlayerCache;
import io.github.jwdeveloper.ff.core.cache.implementation.PlayerCacheImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerContainer {
    private final Container mainContainer;
    private final Map<UUID, Container> playerContainers;
    private final Set<RegistrationInfo> playerScopedClasses;

    public PlayerContainer(Container container, Set<RegistrationInfo> classes) {
        this.mainContainer = container;
        this.playerScopedClasses = classes;
        this.playerContainers = new HashMap<>();
    }

    public Object find(UUID uuid, Class<?> type, Class<?>... genericTypes) {
        var playerContainer = getPlayerContainer(uuid);
        return playerContainer.find(type, genericTypes);
    }

    public void clear(UUID uuid) {
        playerContainers.remove(uuid);
    }

    private Container getPlayerContainer(UUID uuid) {
        if (playerContainers.containsKey(uuid)) {
            return playerContainers.get(uuid);
        }

        var builder = Dependance.newContainer();
        builder.registerTransient(Player.class, container -> Bukkit.getPlayer(uuid));
        builder.registerSingleton(PlayerCache.class, PlayerCacheImpl.class);
        for (var scopedClass : playerScopedClasses) {
            builder.register(scopedClass);
        }
        builder.configure(config ->
        {
            config.onInjection(onInjectionEvent ->
            {
                if (onInjectionEvent.hasOutput()) {
                    return onInjectionEvent.output();
                }
                return mainContainer.find(onInjectionEvent.input(), onInjectionEvent.inputGenericParameters());
            });
        });
        var container = builder.build();
        playerContainers.put(uuid, container);
        return container;
    }
}
