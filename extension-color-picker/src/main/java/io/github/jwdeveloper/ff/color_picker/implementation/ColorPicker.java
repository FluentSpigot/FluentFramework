/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveloper.ff.color_picker.implementation;

import io.github.jwdeveloper.ff.color_picker.api.ColorUtility;
import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.plugin.api.features.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Injection
public class ColorPicker {
    private final Map<Player, Consumer<ColorPickerResult>> players;
    private final FluentTranslator translator;
    private final FluentTaskManager taskManager;
    private final List<org.bukkit.Color> palleteColors;

    @Inject
    public ColorPicker(FluentTranslator translator, FluentTaskManager taskManager) {
        players = new HashMap<>();
        this.translator = translator;
        this.taskManager = taskManager;
        palleteColors = createDefaultColors();
    }

    public void unregister(Player player) {
        players.remove(player);
    }


    public void register(Player player, Consumer<ColorPickerResult> event) {
        players.put(player, event);
        handlePageSelection(player, "#FFFFFF");
    }

    public boolean validate(Player player) {
        return players.containsKey(player);
    }


    public boolean handleColorSelection(Player player, String color, AsyncPlayerChatEvent event) {
        for (var _player : players.keySet()) {
            event.getRecipients().remove(_player);
        }

        if (!validate(player)) {
            return false;
        }
        if (StringUtils.isNullOrEmpty(color)) {
            return false;
        }
        if (!color.startsWith("#")) {
            return false;
        }
        if (color.length() != 7) {
            return false;
        }


        var consumer = players.get(player);
        clearChat(player);
        unregister(player);
        taskManager.task(() ->
        {
            consumer.accept(new ColorPickerResult(color));
        });
        return true;
    }

    public boolean handlePageSelection(Player player, String color) {
        if (!validate(player)) {
            return false;
        }

        var baseColor = ChatColor.of(color);
        var descCompoent = FluentApi.messages().chat()
                .color(org.bukkit.ChatColor.AQUA)
                // .text(translator.get(FluentTranslations.COLOR_PICKER.COMMAND.DESC_1))
                .toTextComponent();


        var titleComponent = FluentApi.messages().chat()
                .color(org.bukkit.ChatColor.AQUA)
                // .text(translator.get(FluentTranslations.COLOR_PICKER.COMMAND.DESC_3))
                .toTextComponent();

        final var avaliableColorsLines = FluentApi.messages().chat()
                .toTextComponent();
        avaliableColorsLines.setExtra(getAvaliableColorsLines());


        clearChat(player);

        player.spigot().sendMessage(descCompoent);
        player.spigot().sendMessage(titleComponent);
        player.sendMessage(" ");
        for (var line : getPageColorPallete(baseColor)) {
            player.spigot().sendMessage(line);
        }
        player.spigot().sendMessage(avaliableColorsLines);
        return true;
    }


    private void clearChat(Player player) {
        for (var i = 0; i < 15; i++) {
            player.sendMessage(" ");
        }
    }

    private List<BaseComponent> getPageColorPallete(ChatColor baseColor) {
        var list = new ArrayList<BaseComponent>();


        var bands = ColorUtility.getColorBands(baseColor.getColor(), 5, true);
        for (var i = 0; i < bands.size(); i++) {
            var root = new MessageBuilder()
                    .space(1)
                    .toTextComponent();


            var band = bands.get(i);
            var variants = ColorUtility.getColorBands(band, 15, false);
            for (var variant : variants) {
                var option = new MessageBuilder()
                        .text(Emoticons.square)
                        .space()
                        .toTextComponent();

                option.setColor(ChatColor.of(variant));
                var hex = ColorUtility.toHex(variant.getRed(), variant.getGreen(), variant.getBlue());
                option.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, hex));
               // option.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new MessageBuilder().color(org.bukkit.ChatColor.AQUA).text(translator.get(FluentTranslations.COPY.INFO)).toString())));
                root.addExtra(option);
            }
            list.add(root);
        }
        return list;
    }

    private List<BaseComponent> getAvaliableColorsLines() {
        var list = new ArrayList<BaseComponent>();
        for (var color : palleteColors) {
            var hex = ColorUtility.toHex(color.getRed(), color.getGreen(), color.getBlue());
            final var colorComponent = FluentApi.messages().chat()
                    .text(Emoticons.boldBar)
                    .toTextComponent();
            colorComponent.setColor(ChatColor.of(hex));
            colorComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/piano colors page " + hex));
            //  final var hover = new Text(translator.get(FluentTranslations.COLOR_PICKER.CHANGE_COLOR));
            // colorComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
            list.add(colorComponent);
        }
        return list;
    }


    private List<org.bukkit.Color> createDefaultColors() {
        var result = new ArrayList<org.bukkit.Color>();
        for (var field : org.bukkit.Color.class.getDeclaredFields()) {
            try {
                if (!field.getType().equals(org.bukkit.Color.class)) {
                    continue;
                }
                field.setAccessible(true);
                var value = (org.bukkit.Color) field.get(null);
                field.setAccessible(false);
                result.add(value);
            } catch (Exception e) {
                FluentLogger.LOGGER.error("Color", e);
            }
        }
        return result;
    }

}
