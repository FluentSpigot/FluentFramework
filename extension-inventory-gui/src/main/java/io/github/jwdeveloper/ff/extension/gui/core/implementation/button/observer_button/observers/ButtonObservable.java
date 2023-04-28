package io.github.jwdeveloper.ff.extension.gui.core.implementation.button.observer_button.observers;

import io.github.jwdeveloper.ff.extension.gui.core.implementation.button.ButtonUI;
import org.bukkit.entity.Player;

public interface ButtonObservable<T>
{
    public ButtonUI getButtonUI();

    public void leftClick(Player player);

    public void rightClick(Player player);

    public void refresh();

    public void setValue(T value);

    public T getValue();
}
