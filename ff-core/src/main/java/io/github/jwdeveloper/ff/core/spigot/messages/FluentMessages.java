package io.github.jwdeveloper.ff.core.spigot.messages;


import io.github.jwdeveloper.ff.core.spigot.messages.boss_bar.BossBarBuilder;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.messages.title.SimpleTitleBuilder;

public class FluentMessages
{

    public SimpleTitleBuilder title() {
        return new SimpleTitleBuilder();
    }

    public MessageBuilder chat() {
        return new MessageBuilder();
    }

    public BossBarBuilder bossBar() {
        return new BossBarBuilder();
    }
}
