package io.github.jwdeveloper.ff.extension.gui.OLD.observers.events;

import io.github.jwdeveloper.ff.extension.gui.OLD.ButtonUIOld;
import org.bukkit.entity.Player;

public record onSelectEvent<T>(ButtonUIOld buttonUI, T data, int index, Player player) {

}
