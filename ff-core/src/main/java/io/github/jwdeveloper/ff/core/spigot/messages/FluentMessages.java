package io.github.jwdeveloper.ff.core.spigot.messages;


import io.github.jwdeveloper.ff.core.spigot.messages.boss_bar.BossBarBuilder;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.messages.text_component.TextComponentBuilder;
import io.github.jwdeveloper.ff.core.spigot.messages.title.SimpleTitleBuilder;
import org.bukkit.command.CommandSender;

public class FluentMessages {
    public SimpleTitleBuilder title() {
        return new SimpleTitleBuilder();
    }

    public MessageBuilder chat()
    {
        return new MessageBuilder();
    }
    public BossBarBuilder bossBar() {
        return new BossBarBuilder();
    }
    public TextComponentBuilder component() {
        return new TextComponentBuilder();
    }

    public void clearChat(CommandSender sender)
    {
        for (var i = 0; i < 15; i++)
        {
            sender.sendMessage(" ");
        }
    }
}
