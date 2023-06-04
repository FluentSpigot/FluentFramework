package io.github.jwdeveloper.ff.extension.resourcepack.implementation;

import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.extension.resourcepack.api.FluentResourcepack;
import io.github.jwdeveloper.ff.extension.resourcepack.implementation.data.ResourcepackConfig;
import io.github.jwdeveloper.ff.extension.resourcepack.implementation.services.ResourcepackLinkService;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class FluentResourcepackImpl implements FluentResourcepack {
    private final ResourcepackConfig config;
    private final ResourcepackLinkService linkService;

    public FluentResourcepackImpl(FluentEventManager eventManager, ResourcepackConfig config, ResourcepackLinkService linkService) {
        this.config = config;
        this.linkService = linkService;
        eventManager.onEvent(PlayerJoinEvent.class, this::onPlayerJoin);
    }

    private void onPlayerJoin(PlayerJoinEvent event)
    {
         if(!config.isLoadOnJoin())
         {
             return;
         }
        downloadResourcepack(event.getPlayer());
    }

    public void downloadResourcepack(Player player)
    {
        player.setResourcePack(config.getUrl());
    }

    public void sendResourcepackInfo(Player player) {
        linkService.send(player, config.getUrl(), "Resourcepack link");
    }
}
