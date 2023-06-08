package io.github.jwdeveloper.ff.extension.gui.implementation.button_old.observer_button.observers;

import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.ButtonUIOld;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class ButtonObserverEvent<T>
{
    private Player player;
    private ButtonUIOld button;
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
