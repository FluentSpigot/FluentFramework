package io.github.jwdeveloper.ff.extension.gui.implementation.observers.events;

import io.github.jwdeveloper.ff.extension.gui.core.implementation.button.ButtonUIOld;
import org.bukkit.entity.Player;

public record onSelectEvent<T>(ButtonUIOld buttonUI, T data, int index, Player player)
{

}
