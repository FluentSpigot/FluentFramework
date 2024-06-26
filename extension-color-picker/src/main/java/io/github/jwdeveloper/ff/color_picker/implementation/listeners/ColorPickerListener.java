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

package io.github.jwdeveloper.ff.color_picker.implementation.listeners;

import io.github.jwdeveloper.dependance.injector.api.annotations.Inject;
import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.color_picker.implementation.ColorPicker;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

@Injection(lazyLoad = false)
public class ColorPickerListener extends EventBase
{
    private final ColorPicker colorPicker;

    @Inject
    public ColorPickerListener(Plugin plugin, ColorPicker colorPicker)
    {
        super(plugin);
        this.colorPicker = colorPicker;
    }


    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        colorPicker.unregister(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        colorPicker.unregister(event.getPlayer());
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event)
    {
        event.setCancelled(colorPicker.handleColorSelection(event.getPlayer(), event.getMessage(),event));
    }
}
