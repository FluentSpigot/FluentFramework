package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import lombok.Value;
import org.bukkit.entity.Player;

import java.util.List;

@Value
public class ContentSelectionEvent<T>
{
    ButtonUI button;
    T selectedItem;
    List<T> contentSource;
    int index;
    Player player;
    FluentInventory inventory;
}
