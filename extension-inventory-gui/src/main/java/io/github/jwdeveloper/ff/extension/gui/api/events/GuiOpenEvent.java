package io.github.jwdeveloper.ff.extension.gui.api.events;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import java.util.Optional;

@AllArgsConstructor
public class GuiOpenEvent implements Cancellable {

    @Getter
    @Setter
    private boolean Cancelled;
    @Getter
    private FluentInventory inventory;

    @Getter
    private Player player;

    @Getter
    private Object[] arguments;


    public <T> Optional<T> tryGetArgument(Class<T> type) {
        try {
            return Optional.of((T)getArgument(type));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public <T> T getArgument(Class<T> type) {
        for (var argument : arguments) {
            if (argument.getClass().isAssignableFrom(type)) {
                return (T) argument;
            }
        }
        throw new RuntimeException("Argument not found for type " + type.getSimpleName());
    }

    public boolean hasArgument(Class<?> type) {
        for (var argument : arguments) {
            if (argument.getClass().isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }
}
