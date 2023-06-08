package io.github.jwdeveloper.ff.extension.gui.prefab.components.common.search;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import lombok.Value;
import org.bukkit.entity.Player;

@Value
public class SearchGuiEvent
{
    Player player;
    FluentInventory inventory;
    ButtonUI buttonUI;
    String query;
    SearchFilter filter;
}
