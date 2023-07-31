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

package io.github.jwdeveloper.ff.extension.resourcepack.implementation.services;
import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class ResourcepackLinkService
{
    FluentMessages fluentMessages;
    FluentTranslator tr;

    public ResourcepackLinkService(FluentMessages fluentMessages, FluentTranslator translator) {
        this.fluentMessages = fluentMessages;
        this.tr = translator;
    }

    public void send(Player player, String link, String title)
    {
        //TODO
     /*   var copyToClipboardComponent =  fluentMessages.chat().color(ChatColor.AQUA)
            .color(ChatColor.BOLD)
            .text(Emoticons.arrowRight)
            .space()
            .text(tr.get("resourcepack.copy-to-clipboard"))
            .toTextComponent();


        copyToClipboardComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, link));
        copyToClipboardComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(tr.get("resourcepack.copy.info"))));

        var copyToChatComponent =  new MessageBuilder().color(ChatColor.AQUA)
                .color(ChatColor.BOLD)
                .text(Emoticons.arrowRight)
                .space()
                .text(tr.get("resourcepack.copy.to-chat"))
                .toTextComponent();
        copyToChatComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, link));
        copyToChatComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(tr.get("resourcepack.copy.info"))));

        fluentMessages.chat().info().text(title).send(player);
        player.getPlayer().sendMessage(" ");
        player.getPlayer().spigot().sendMessage(copyToClipboardComponent);
        player.getPlayer().spigot().sendMessage(copyToChatComponent);*/

    }
}
