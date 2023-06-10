package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.search;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import lombok.Value;
import org.bukkit.entity.Player;

@Value
public class SearchGuiEvent
{
    Player player;
    FluentInventory inventory;
    String query;
    SearchFilter filter;
}
