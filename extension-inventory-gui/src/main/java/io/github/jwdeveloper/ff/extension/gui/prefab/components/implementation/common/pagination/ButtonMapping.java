package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.pagination;

import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;

@FunctionalInterface
public interface ButtonMapping<T>
{
    void onMapping(T data, ButtonUI buttonUI);
}
