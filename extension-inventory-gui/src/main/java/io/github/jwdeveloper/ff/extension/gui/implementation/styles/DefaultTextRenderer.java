package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.core.common.ColorPallet;
import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.text_renderer.TextElementRenderer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;


public class DefaultTextRenderer implements TextElementRenderer {
    private final Plugin plugin;

    @Getter
    @Setter
    private ColorPallet colorPallet;

    public DefaultTextRenderer(Plugin plugin, ColorPallet colorPallet) {
        this.plugin = plugin;
        this.colorPallet = colorPallet;
    }


    @Override
    public String getInfo(String title) {
        return builder().color(colorPallet.getTextBight()).text(title).toString();
    }

    @Override
    public String getWarning(String title) {
        return null;
    }

    @Override
    public String getError(String title) {
        return null;
    }

    @Override
    public String getSuccess(String title) {
        return null;
    }

    @Override
    public String getPrefix(String title)
    {
        return null;
        //  return builder().inBrackets(title, colorPallet.getTextBight(), colorPallet.getPrimary());
    }

    @Override
    public String getBar(int length) {
        return builder().bar(Emoticons.boldBar, length, colorPallet.getPrimary()).toString();
    }

    @Override
    public String getText(String text) {
        return builder().color(colorPallet.getTextBight()).text(text).toString();
    }

    @Override
    public String getListMember(String text) {
        return builder().color(colorPallet.getPrimary()).text(Emoticons.dot).space().text(text).toString();
    }

    @Override
    public String getProperty(String name, Object value) {
        return builder().color(colorPallet.getPrimary()).text(Emoticons.dot).space().text(name).space().color(colorPallet.getSecondary()).text(Emoticons.arrowRight).space().text(value).space().reset().toString();
    }

    @Override
    public String getCheckbox(String name, boolean isChecked) {
        return null;
    }


    private MessageBuilder builder() {
        return new MessageBuilder();
    }

}
