package io.github.jwdeveloper.ff.core.spigot.messages.text_component;

import io.github.jwdeveloper.ff.core.common.ColorUtility;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class TextComponentBuilder {
    private final TextComponent component;

    public TextComponentBuilder() {
        component = new TextComponent();
    }

    public TextComponentBuilder withText(Consumer<MessageBuilder> consumer) {
        var builder = new MessageBuilder();
        consumer.accept(builder);
        component.setText(builder.toString());
        return this;
    }

    public TextComponentBuilder withTextComponent(List<BaseComponent> components) {
        for (var component : components) {
            withTextComponent(component);
        }
        return this;
    }

    public TextComponentBuilder withTextComponent(BaseComponent baseComponent) {
        component.addExtra(baseComponent);
        return this;
    }

    public <T extends BaseComponent> TextComponentBuilder withComponents(List<T> components)
    {
        components.forEach(component::addExtra);
        return this;
    }


    public TextComponentBuilder withTextComponent(Consumer<TextComponentBuilder> consumer) {
        var builder = new TextComponentBuilder();
        consumer.accept(builder);
        component.addExtra(builder.toComponent());
        return this;
    }

    public TextComponentBuilder withText(String text) {
        component.setText(text);
        return this;
    }

    public TextComponentBuilder withUnderline() {
        component.setUnderlined(true);
        return this;
    }

    public TextComponentBuilder withItalic() {
        component.setItalic(true);
        return this;
    }

    public TextComponentBuilder withBold() {
        component.setBold(true);
        return this;
    }

    public TextComponentBuilder withFont(String font) {
        component.setFont(font);
        return this;
    }


    public TextComponentBuilder withHoverEvent(HoverEvent.Action action, Consumer<TextComponentBuilder> consumer) {
        var builder = new TextComponentBuilder();
        consumer.accept(builder);
        component.setHoverEvent(new HoverEvent(action, new BaseComponent[]{builder.toComponent()}));
        return this;
    }

    public TextComponentBuilder withHoverEvent(HoverEvent.Action action, Content... value) {
        component.setHoverEvent(new HoverEvent(action, value));
        return this;
    }

    public TextComponentBuilder withClickEvent(ClickEvent.Action action, String value) {
        component.setClickEvent(new ClickEvent(action, value));
        return this;
    }

    public TextComponentBuilder withClickEvent(ClickEvent event) {
        component.setClickEvent(event);
        return this;
    }

    public TextComponentBuilder withBungeeChatColor(net.md_5.bungee.api.ChatColor color) {
        component.setColor(color);
        return this;
    }

    public TextComponentBuilder withBukkitChatColor(org.bukkit.ChatColor color) {
        component.setColor(color.asBungee());
        return this;
    }

    public TextComponentBuilder withJavaColor(Color color) {
        component.setColor(ChatColor.of(color));
        return this;
    }

    public TextComponentBuilder withHexColor(String color) {
        component.setColor(ChatColor.of(color));
        return this;
    }

    public TextComponentBuilder withRgbColor(int r, int g, int b) {
        var color = ColorUtility.toHex(r, g, b);
        return withHexColor(color);
    }

    public TextComponentBuilder withBukkitColor(org.bukkit.Color color) {
        return withRgbColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public TextComponent toComponent() {
        return component;
    }

    public String toString() {
        return component.toPlainText();
    }

    public void send(CommandSender sender) {
        sender.spigot().sendMessage(toComponent());
    }
    public void sendActionBar(CommandSender sender)
    {
        if(sender instanceof Player player)
        {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, toComponent());
        }
    }

}
