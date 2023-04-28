package io.github.jwdeveloper.ff.extension.gui.inventory.observers.events;

import io.github.jwdeveloper.ff.extension.gui.core.implementation.button.ButtonUI;
import org.bukkit.entity.Player;

public record onSelectEvent<T>(ButtonUI buttonUI, T data, int index, Player player)
{

}
