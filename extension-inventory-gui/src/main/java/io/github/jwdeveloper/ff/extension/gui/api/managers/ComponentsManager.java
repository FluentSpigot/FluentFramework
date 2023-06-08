package io.github.jwdeveloper.ff.extension.gui.api.managers;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;

import java.util.List;

public interface ComponentsManager
{
    void add(List<InventoryComponent> components);
    void add(InventoryComponent component);

    void remove(InventoryComponent component);

    <T extends InventoryComponent> void remove(Class<T> clazz);

    <T extends InventoryComponent> T find(Class<T> clazz);

    <T extends InventoryComponent> T findOrThrow(Class<T> clazz);
}
