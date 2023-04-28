package io.github.jwdeveloper.ff.extension.gui.core.implementation.button.observer_button.observers;

import io.github.jwdeveloper.ff.extension.gui.core.implementation.button.ButtonUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class ButtonObserverEvent<T>
{
    private Player player;
    private ButtonUI button;
    private ButtonObservable<T> observer;
    private T value;

    @Override
    public String toString() {
        return "ButtonObserverEvent{" +
                "player=" + player +
                ", button=" + button +
                ", observer=" + observer +
                ", value=" + value +
                '}';
    }
}
