package io.github.jwdeveloper.ff.extension.gui.implementation.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class ButtonClickEvent
{
    Player player;
    ButtonUI buttonGui;
}
