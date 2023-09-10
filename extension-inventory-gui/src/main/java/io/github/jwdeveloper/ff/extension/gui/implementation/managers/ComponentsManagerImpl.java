package io.github.jwdeveloper.ff.extension.gui.implementation.managers;

import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.managers.ComponentsManager;

import java.util.LinkedList;
import java.util.List;

public class ComponentsManagerImpl implements ComponentsManager {
    private final List<InventoryComponent> components;
    private final PluginLogger logger;


    public ComponentsManagerImpl(PluginLogger logger, List<InventoryComponent> components)
    {
        this.components = new LinkedList<>(components);
        this.logger = logger;
    }

    @Override
    public void add(List<InventoryComponent> components) {
        this.components.addAll(components);
    }

    @Override
    public void add(InventoryComponent... components) {
        this.components.addAll(List.of(components));
    }

    @Override
    public void add(InventoryComponent components) {
        this.components.add(components);
    }

    @Override
    public void remove(InventoryComponent components) {
        this.components.remove(components);
    }

    @Override
    public <T extends InventoryComponent> void remove(Class<T> clazz) {
        var result = find(clazz);
        if (result == null) {
            return;
        }
        remove(result);
    }

    @Override
    public <T extends InventoryComponent> T find(Class<T> clazz) {
        try {
            return findOrThrow(clazz);
        } catch (Exception e) {
            logger.warning("Component of type: " + clazz.getSimpleName() + " not found");
            return null;
        }
    }

    @Override
    public <T extends InventoryComponent> T findOrThrow(Class<T> clazz) {
        var optional = components.stream().filter(e -> e.getClass().isAssignableFrom(clazz)).findAny();
        if (optional.isEmpty()) {
            throw new RuntimeException("Inventory don't have component of type: " + clazz.getSimpleName());
        }
        return (T) optional.get();
    }

    @Override
    public List<InventoryComponent> findAll() {
        return components;
    }
}
