package io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.implementation;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.core.injector.api.containers.FluentContainer;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.injector.api.models.RegistrationInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FluentPlayerContext {
    private final ConcurrentHashMap<UUID, Container> playerContainers;
    private final FluentContainer mainContainer;
    private final List<RegistrationInfo> registrationInfos;
    private final FluentPlayerContextListener listener;
    private final PluginLogger logger;

    public FluentPlayerContext(FluentContainer mainContainer,
                               List<RegistrationInfo> registrationInfos,
                               FluentPlayerContextListener listener,
                               PluginLogger logger)
    {
        playerContainers = new ConcurrentHashMap<>();
        this.mainContainer = mainContainer;
        this.registrationInfos = registrationInfos;
        this.listener = listener;
        this.logger = logger;
        listener.setOnPlayerLeave(this::clear);
    }

    public <T> T find(Class<T> injectionType, Player player) {
        return find(injectionType, player.getUniqueId());
    }
    public <T> T find(Class<T> injectionType, UUID uuid) {

        if (!playerContainers.containsKey(uuid)) {
            try {
                playerContainers.put(uuid, createContainer(uuid));
            } catch (Exception e) {
                logger.error("Unable register container for player " + uuid.toString(), e);
                return null;
            }
        }
        final var container = playerContainers.get(uuid);
        return (T) container.find(injectionType);
    }

    public void clear(Player player) {
        clear(player.getUniqueId());
    }

    public void clear(UUID player) {

        playerContainers.remove(player);
    }


    private Container createContainer(UUID uuid) throws Exception {
        return new PlayerContainerBuilderImpl(logger)
                .setParentContainer(mainContainer)
                .configure(containerConfiguration ->
                {
                    containerConfiguration.addRegistration(registrationInfos);
                })
                .register(Player.class, LifeTime.PLAYER_SCOPE, container ->
                {
                    listener.register(uuid);
                    return Bukkit.getPlayer(uuid);
                })
                .build();
    }
}
