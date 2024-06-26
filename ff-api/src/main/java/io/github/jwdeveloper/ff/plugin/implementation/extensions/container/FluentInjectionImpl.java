package io.github.jwdeveloper.ff.plugin.implementation.extensions.container;


import io.github.jwdeveloper.dependance.injector.api.containers.Container;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player.PlayerContainer;
import org.bukkit.entity.Player;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.UUID;

public class FluentInjectionImpl implements FluentInjection {
    private Container pluginContainer;
    private PlayerContainer playerContainer;

    public FluentInjectionImpl(Container pluginContainer, PlayerContainer playerContainer) {
        this.pluginContainer = pluginContainer;
        this.playerContainer = playerContainer;
    }

    @Override
    public <T> T tryFindInjection(Class<T> injectionType) {
        try {
            return findInjection(injectionType);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T findInjection(Class<T> injectionType) {
        return (T) pluginContainer.find(injectionType);
    }

    public <T> T findInjection(Class<T> injectionType, Type... genericTypes) {
        return (T) pluginContainer.find(injectionType, genericTypes);
    }

    @Override
    public <T> Collection<T> findAllByInterface(Class<T> _interface) {
        return pluginContainer.findAllByInterface(_interface);
    }

    @Override
    public <T> Collection<T> findAllBySuperClass(Class<T> superClass) {
        return pluginContainer.findAllBySuperClass(superClass);
    }

    @Override
    public Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation) {
        return pluginContainer.findAllByAnnotation(_annotation);
    }

    public <T> T findPlayerScopeInjection(Class<T> injectionType, Player player) {

        return (T) playerContainer.find(player.getUniqueId(), injectionType);
    }

    public <T> T findPlayerScopeInjection(Class<T> injectionType, UUID playerUuid) {
        return (T) playerContainer.find(playerUuid, injectionType);
    }

    @Override
    public void clearPlayerScope(Player player) {
        playerContainer.clear(player.getUniqueId());
    }

    @Override
    public void clearPlayerScope(UUID player) {
        playerContainer.clear(player);
    }

    public Container getContainer() {
        return pluginContainer;
    }
}
