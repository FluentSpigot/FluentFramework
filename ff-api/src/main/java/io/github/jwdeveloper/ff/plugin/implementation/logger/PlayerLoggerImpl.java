package io.github.jwdeveloper.ff.plugin.implementation.logger;

import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.messages.text_component.TextComponentBuilder;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLoggerConfig;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;

public class PlayerLoggerImpl implements PlayerLogger {
    private final FluentMessages fluentMessages;
    private final PlayerLoggerConfig config;

    public PlayerLoggerImpl(PlayerLoggerConfig config, FluentMessages fluentMessages) {
        this.config = config;
        this.fluentMessages = fluentMessages;
    }

    @Override
    public MessageBuilder info(Object... values) {
        return getPrefix(fluentMessages.chat(), "", ChatColor.AQUA).text(values);
    }

    @Override
    public TextComponentBuilder link(String title, String url) {
        return fluentMessages.component().withText(builder ->
                {
                    getPrefix(builder, "", ChatColor.AQUA).color(ChatColor.DARK_AQUA).bold().text("Click here").space().color(ChatColor.GRAY).text(title);
                })
                .withHoverEvent(HoverEvent.Action.SHOW_TEXT, (e) ->
                {
                    e.withText(title);
                })
                .withClickEvent(ClickEvent.Action.OPEN_URL, url);
    }

    @Override
    public MessageBuilder error(Object... values) {
        return getPrefix(fluentMessages.chat(), "Error", ChatColor.RED).text(values);
    }

    @Override
    public MessageBuilder error(Throwable throwable, Object... values) {
        return getPrefix(fluentMessages.chat(), "Error", ChatColor.RED).text(values);
    }

    @Override
    public MessageBuilder warning(Object... values) {
        return getPrefix(fluentMessages.chat(), "", ChatColor.YELLOW).text(values);
    }

    @Override
    public MessageBuilder success(Object... values) {
        return getPrefix(fluentMessages.chat(), "", ChatColor.GREEN).text(values);
    }


    private MessageBuilder getPrefix(MessageBuilder builder, String name, ChatColor color) {
        builder.text(color)
                .bold().text("[").reset().text(color)
                .text(config.getPrefix()).text(name)
                .bold().text("]")
                .text(ChatColor.RESET)
                .space()
                .color(config.getColorPallet().getTextDark())
                .color(ChatColor.ITALIC);
        return builder;
    }
}
