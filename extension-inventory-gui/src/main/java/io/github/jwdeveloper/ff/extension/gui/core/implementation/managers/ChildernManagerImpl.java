package io.github.jwdeveloper.ff.extension.gui.core.implementation.managers;

import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.ChildrenManager;

public class ChildernManagerImpl implements ChildrenManager {

    FluentInventory parent;

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public FluentInventory getParent()
    {
         return parent;
    }

    @Override
    public void addChild(FluentInventory child) {

    }

    @Override
    public void removeChild(FluentInventory child) {

    }

    @Override
    public FluentInventory[] children() {
        return new FluentInventory[0];
    }
}
