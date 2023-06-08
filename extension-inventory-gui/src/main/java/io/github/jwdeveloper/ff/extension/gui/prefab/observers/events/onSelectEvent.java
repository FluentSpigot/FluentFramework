package io.github.jwdeveloper.ff.extension.gui.prefab.observers.events;

import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.ButtonUIOld;
import org.bukkit.entity.Player;

public record onSelectEvent<T>(ButtonUIOld buttonUI, T data, int index, Player player)
{

}
