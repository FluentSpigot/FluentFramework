package io.github.jwdeveloper.ff.extension.gui.core.api.managers;

import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;

public interface ChildrenManager
{
    boolean hasParent();
    FluentInventory getParent();

    void addChild(FluentInventory child);

    void removeChild(FluentInventory child);

    FluentInventory[] children();
}
