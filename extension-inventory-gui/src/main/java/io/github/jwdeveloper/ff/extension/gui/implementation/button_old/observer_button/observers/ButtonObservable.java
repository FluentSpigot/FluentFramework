package io.github.jwdeveloper.ff.extension.gui.implementation.button_old.observer_button.observers;

import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.ButtonUIOld;
import org.bukkit.entity.Player;

public interface ButtonObservable<T>
{
    public ButtonUIOld getButtonUI();

    public void leftClick(Player player);

    public void rightClick(Player player);

    public void refresh();

    public void setValue(T value);

    public T getValue();
}
