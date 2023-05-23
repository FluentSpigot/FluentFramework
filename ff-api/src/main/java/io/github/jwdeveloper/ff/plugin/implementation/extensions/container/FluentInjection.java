package io.github.jwdeveloper.ff.plugin.implementation.extensions.container;

import org.bukkit.entity.Player;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.UUID;

public interface FluentInjection {
    <T> T tryFindInjection(Class<T> injectionType);

    <T> T findInjection(Class<T> injectionType);

    <T> Collection<T> findAllByInterface(Class<T> _interface);

    <T> Collection<T> findAllBySuperClass(Class<T> superClass);

    Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation);

    <T> T findPlayerScopeInjection(Class<T> injectionType, Player player);

    <T> T findPlayerScopeInjection(Class<T> injectionType, UUID player);

    void clearPlayerScope(Player player);

    void clearPlayerScope(UUID player);

}
