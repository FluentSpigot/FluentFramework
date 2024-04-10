package io.github.jwdeveloper.ff.core.spigot.messages.message;

import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public class MessageBuilder extends TextBuilder<MessageBuilder> {
    public MessageBuilder reset() {
        builder.append(ChatColor.RESET);
        return this;
    }

    public <T> MessageBuilder addList(List<T> name, Consumer<T> action) {
        for (var value : name) {
            action.accept(value);
        }
        return this;
    }


    public MessageBuilder gradient(String text,  Color... colors) {
        int length = text.length();
        int numTransitions = colors.length - 1;
        double segment = length / (double) numTransitions;

        for (int i = 0; i < length; i++) {
            int segmentIndex = (int) (i / segment); // Determine which segment the character belongs to
            double localRatio = (i % segment) / segment;

            if (segmentIndex >= colors.length - 1) {
                segmentIndex = colors.length - 2; // Handle edge case for last segment
                localRatio = 1.0;
            }

            // Calculate the interpolated color
            Color startColor = colors[segmentIndex];
            Color endColor = colors[segmentIndex + 1];
            int red = (int) (startColor.getRed() * (1 - localRatio) + endColor.getRed() * localRatio);
            int green = (int) (startColor.getGreen() * (1 - localRatio) + endColor.getGreen() * localRatio);
            int blue = (int) (startColor.getBlue() * (1 - localRatio) + endColor.getBlue() * localRatio);
            this.color(red,green,blue).text(String.valueOf(text.charAt(i)));
        }

        return this;
    }

    public String getColor(float degrees, float saturation, float brightness) {
        var c = java.awt.Color.getHSBColor(degrees, saturation, brightness);
        return net.md_5.bungee.api.ChatColor.of(c) + "";
    }

    public MessageBuilder gray() {
        builder.append(ChatColor.GRAY);
        return this;
    }

    public MessageBuilder green() {
        builder.append(ChatColor.GREEN);
        return this;
    }

    public MessageBuilder darkGreen() {
        builder.append(ChatColor.DARK_GREEN);
        return this;
    }

    public MessageBuilder red() {
        builder.append(ChatColor.RED);
        return this;
    }

    public MessageBuilder darkRed() {
        builder.append(ChatColor.DARK_RED);
        return this;
    }

    public MessageBuilder white() {
        builder.append(ChatColor.WHITE);
        return this;
    }

    public MessageBuilder addList(List<String> name, boolean mark) {
        for (var value : name) {
            if (mark) {
                this.text(" -" + value).newLine();
            } else {
                this.text(" ").text(value).newLine();
            }

        }
        return this;
    }

    public MessageBuilder addList(List<String> name) {
        return addList(name, false);
    }


    public MessageBuilder location(Location location) {
        color(ChatColor.AQUA).text("World ").color(ChatColor.WHITE).text(location.getWorld().getName()).space();
        color(ChatColor.AQUA).text("X ").color(ChatColor.WHITE).text(location.getX()).space();
        color(ChatColor.AQUA).text("Y ").color(ChatColor.WHITE).text(location.getY()).space();
        color(ChatColor.AQUA).text("Z ").color(ChatColor.WHITE).text(location.getZ()).space();
        return this;
    }

    public MessageBuilder bar(String bar, int length, String color) {
        return this.color(color).bar(bar, length).reset();
    }

    public MessageBuilder bar(String bar, int length, ChatColor color) {
        return this.color(color).bar(bar, length).reset();
    }

    public MessageBuilder textFormat(String pattern, Object... args) {
        return text(String.format(pattern, args));
    }

    public MessageBuilder color(ChatColor chatColor) {
        builder.append(chatColor);
        return this;
    }


    public MessageBuilder color(int r, int g, int b) {
        builder.append(net.md_5.bungee.api.ChatColor.of(new java.awt.Color(r, g, b)));
        return this;
    }

    public MessageBuilder bold(Object text) {
        builder.append(ChatColor.BOLD).append(text);
        return this;
    }

    public MessageBuilder bold() {
        builder.append(ChatColor.BOLD);
        return this;
    }

    public MessageBuilder color(String color) {
        builder.append(color);
        return this;
    }

    public MessageBuilder space(int count) {
        for (; count > 0; count--) {
            space();
        }
        return this;
    }

    public MessageBuilder space() {
        builder.append(" ");
        return this;
    }

    public MessageBuilder inBrackets(String message) {
        builder.append("[").append(message).append("]").append(ChatColor.RESET);
        return this;
    }


    public MessageBuilder inBrackets(String message, ChatColor color) {
        builder.append(color).append("[").append(message).append("]");
        return this;
    }

    public MessageBuilder inBrackets(String message, ChatColor color, ChatColor colorBorder) {
        builder.append(colorBorder).append("[").append(color).append(message).append(colorBorder).append("]");
        return this;
    }

    public MessageBuilder withFix(String message, String fix) {
        builder.append(fix).append(message).append(fix);
        return this;
    }

    public MessageBuilder withFix(String message, String prefix, String endfix) {
        builder.append(prefix).append(message).append(endfix);
        return this;
    }


    public MessageBuilder withPrefix(String message, String prefix) {
        builder.append(prefix).append(message);
        return this;
    }

    public MessageBuilder withEndfix(String message, String endfix) {
        builder.append(message).append(endfix);
        return this;
    }

    public MessageBuilder underLine(String message) {
        builder.append(ChatColor.UNDERLINE).append(message).append(ChatColor.RESET);
        return this;
    }

    public MessageBuilder strikeThrough(String message) {
        builder.append(ChatColor.STRIKETHROUGH).append(message).append(ChatColor.RESET);
        return this;
    }

    public String[] toArray() {

        return toString().split(System.lineSeparator());
    }

    @Override
    public String toString() {
        return builder.toString();
    }


    public void send(CommandSender... receivers) {
        var messages = toArray();
        for (var receiver : receivers) {
            for (var message : messages) {
                receiver.sendMessage(message);
            }
        }
    }

    public void sendActionBar(Player player) {
        var tc = new TextComponent();
        tc.setText(this.toString());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, tc);
    }

    public void sendToConsole() {
        if (Bukkit.getServer() == null) {

            System.out.println(builder.toString());
            return;
        }
        Bukkit.getConsoleSender().sendMessage(toString());
    }

    public void sendLog() {
        sendLog("");
    }

    public void sendLog(String prefix) {

        var message = builder.toString();
        if (!StringUtils.isNullOrEmpty(prefix)) {

            message = prefix + message;
        }

        if (Bukkit.getServer() == null) {
            System.out.println(message);
            return;
        }
        FluentLogger.LOGGER.info(message);
    }


    public void sendToAllPlayer() {
        if (Bukkit.getServer() == null) {
            System.out.println(builder.toString());
            return;
        }
        for (var player : Bukkit.getOnlinePlayers()) {
            send(player);
        }
    }
}
