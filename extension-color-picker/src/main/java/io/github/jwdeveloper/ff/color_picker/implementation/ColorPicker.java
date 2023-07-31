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

import io.github.jwdeveloper.ff.color_picker.api.ColorPickerResult;
import io.github.jwdeveloper.ff.core.common.ColorUtility;
import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.messages.text_component.TextComponentBuilder;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiMeta;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ColorPicker {

    private final FluentTranslator translator;
    private final FluentTaskFactory tasks;
    private final FluentMessages messages;
    private final FluentApiMeta apiMeta;
    private final Map<Player, Consumer<ColorPickerResult>> players;
    private final List<String> colors;

    public ColorPicker(FluentTranslator translator, FluentTaskFactory taskManager, FluentMessages messages, FluentApiMeta fluentApiMeta) {
        players = new HashMap<>();
        colors = getDefaultHexColors();
        this.translator = translator;
        this.tasks = taskManager;
        this.messages = messages;
        this.apiMeta = fluentApiMeta;
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
        messages.clearChat(player);
        unregister(player);
        tasks.task(() ->
        {
            consumer.accept(new ColorPickerResult(color));
        });
        return true;
    }

    public boolean handlePageSelection(Player player, String color) {
        if (!validate(player)) {
            return false;
        }

        messages.clearChat(player);
        messages.component()
                .withBukkitChatColor(org.bukkit.ChatColor.AQUA)
                .withText(translator.get("command.color-picker.desc-1"))
                .send(player);

        messages.component()
                .withBukkitChatColor(org.bukkit.ChatColor.AQUA)
                .withText(translator.get("command.color-picker.desc-2"))
                .send(player);

        messages.chat().space().send(player);
        for (var palletLine : getPageColorPallet(ChatColor.of(color))) {
            palletLine.send(player);
        }
        messages.component().withTextComponent(getAvailableColorsLines()).send(player);
        return true;
    }


    private List<TextComponentBuilder> getPageColorPallet(ChatColor baseColor) {
        var components = new ArrayList<TextComponentBuilder>();
        var colorBands = ColorUtility.getColorBands(baseColor.getColor(), 5, true);
        for (var i = 0; i < colorBands.size(); i++) {
            var rootComponent = messages.component().withText(" ");

            var colorBand = colorBands.get(i);
            var colorBandVariants = ColorUtility.getColorBands(colorBand, 15, false);
            for (var variantColor : colorBandVariants) {
                var variantHex = ColorUtility.toHex(variantColor.getRed(), variantColor.getGreen(), variantColor.getBlue());
                var builder = messages.component()
                        .withText(e -> e.text(Emoticons.square).space())
                        .withJavaColor(variantColor)
                        .withClickEvent(ClickEvent.Action.SUGGEST_COMMAND, variantHex)
                        .withHoverEvent(HoverEvent.Action.SHOW_TEXT, b ->
                        {
                            b.withText(translator.get("gui.base.copy.info"));
                        });
                rootComponent.withTextComponent(builder.toComponent());
            }
            components.add(rootComponent);
        }
        return components;
    }

    private List<BaseComponent> getAvailableColorsLines() {
        var lines = new ArrayList<BaseComponent>();
        var baseCommand = "/" + apiMeta.getDefaultCommandName() + " colors page ";
        for (var color : colors) {
            var line = messages.component()
                    .withText(Emoticons.boldBar)
                    .withHexColor(color)
                    .withClickEvent(ClickEvent.Action.RUN_COMMAND, baseCommand + color)
                    .withHoverEvent(HoverEvent.Action.SHOW_TEXT, e ->
                    {
                        e.withText(translator.get("command.color-picker.change"));
                    });
            lines.add(line.toComponent());
        }
        return lines;
    }


    private List<String> getDefaultHexColors() {
        return ColorUtility.getBukkitColors()
                .stream()
                .map(e -> ColorUtility.toHex(e.getRed(), e.getGreen(), e.getBlue()))
                .toList();

    }

}
