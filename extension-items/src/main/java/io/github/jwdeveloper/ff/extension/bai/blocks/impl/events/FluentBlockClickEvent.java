package io.github.jwdeveloper.ff.extension.bai.blocks.impl.events;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import lombok.Data;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@Data
public class FluentBlockClickEvent {
    private final FluentBlockInstance fluentBlockInstance;
    private final Player player;

    private final BlockFace blockFace;
    private final Action action;
    private final PlayerInteractEvent spigotEvent;
}
