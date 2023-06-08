package io.github.jwdeveloper.ff.extension.gui.implementation.button_old.events;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class ButtonClickEvent {
    private final Player player;
    private final ButtonUI button;
    private final FluentInventory inventory;
}
