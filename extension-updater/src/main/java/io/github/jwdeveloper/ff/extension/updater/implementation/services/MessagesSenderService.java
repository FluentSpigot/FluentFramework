package io.github.jwdeveloper.ff.extension.updater.implementation.services;

import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.updater.api.info.UpdateInfoResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MessagesSenderService
{
    private final Set<UUID> playersSeenMessages;
    private final Plugin plugin;
    private final FluentMessages fluentMessages;

    private final FluentTranslator translator;

    private UpdateInfoResponse infoResponse;

    public MessagesSenderService(FluentEventManager eventManager,
                                 FluentMessages fluentMessages,
                                 FluentTranslator translator,
                                 Plugin plugin) {
        this.plugin = plugin;
        this.fluentMessages = fluentMessages;
        this.translator = translator;
        playersSeenMessages = new HashSet<>();
        eventManager.onEvent(PlayerJoinEvent.class, this::onPlayerJoin);
    }

    private void onPlayerJoin(PlayerJoinEvent event)
    {
        final var player = event.getPlayer();
        final var uuid = player.getUniqueId();
        if(!player.isOp())
        {
            return;
        }
        if(playersSeenMessages.contains(uuid))
        {
            return;
        }
        if(infoResponse == null)
        {
            return;
        }
        sendUpdateInfoMessage(player, infoResponse);
        playersSeenMessages.add(uuid);
    }

    public void sendUpdateInfoMessage(CommandSender commandSender, UpdateInfoResponse response)
    {
        if(response == UpdateInfoResponse.NOT_FOUND)
        {
            return;
        }
        this.infoResponse = response;
      //  getMessagePrefix().text(translator.get("updater.info.msg-1")).send(commandSender);
      //  getMessagePrefix().text(translator.get("updater.info.msg-2")).text(ChatColor.AQUA).space().text("TODO ADD COMMAND NAME").send(commandSender);
      //  getMessagePrefix().text(translator.get("updater.info.msg-3")).send(commandSender);
        getMessagePrefix()
                .text("New version detected")
                .text("Version:").text(infoResponse.getVersion())
                .text("Description:").text(infoResponse.getDescription()).send(commandSender);
    }

    public MessageBuilder getMessagePrefix() {
        var msg = fluentMessages.chat().inBrackets(plugin.getName());
        return msg.space().color(ChatColor.AQUA).inBrackets("Updater").color(ChatColor.GRAY).space();
    }
}
