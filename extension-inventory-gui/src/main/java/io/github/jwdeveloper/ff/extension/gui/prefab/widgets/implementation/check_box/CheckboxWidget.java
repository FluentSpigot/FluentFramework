package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box;

import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.common.java.JavaUtils;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderEvent;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidget;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class CheckboxWidget implements ButtonWidget {

    private final CheckBoxOptions options;
    private final ButtonRef buttonRef;

    public CheckboxWidget(CheckBoxOptions options) {
        this.options = options;
        buttonRef = new ButtonRef();
    }

    @Override
    public void onCreate(ButtonBuilder builder)
    {
        JavaUtils.throwIfNull(options.itemObserver, "ItemObserver must not be null");

        options.infoMessage = JavaUtils.ifNull(options.infoMessage, "On / Off");
        options.prefix = JavaUtils.ifNull(options.prefix, StringUtils.EMPTY);
        options.enabled = JavaUtils.ifNull(options.enabled,  new MessageBuilder().inBrackets(Emoticons.yes, ChatColor.GREEN,ChatColor.GRAY).toString());
        options.disabled = JavaUtils.ifNull(options.disabled, new MessageBuilder().inBrackets(Emoticons.no, ChatColor.DARK_RED,ChatColor.GRAY).toString());
        options.enableMaterial = JavaUtils.ifNull(options.enableMaterial, Material.LIME_CONCRETE);
        options.disableMaterial = JavaUtils.ifNull(options.disableMaterial, Material.RED_CONCRETE);

        builder.withStyleRenderer(render ->
        {
            render.withLeftClickInfo(options.infoMessage);
            if(options.isCanRender())
            {
                render.withDescriptionLine(options.getId(),this::onRender);
            }
        });
        builder.withOnLeftClick(this::onLeftClick);
        builder.withReference(buttonRef);
    }


    public void onLeftClick(ButtonClickEvent event)
    {
         options.itemObserver.set(!options.itemObserver.get());
    }

    protected String onRender(StyleRenderEvent event)
    {
        var value = options.itemObserver.get();
        var button = buttonRef.get();
        var builder = event.builder();
        if (value)
        {
            button.setHighlighted(true);
            button.setMaterial(options.enableMaterial);
            builder.field(options.prefix, options.enabled);
        }
        else
        {
            button.setHighlighted(false);
            button.setMaterial(options.disableMaterial);
            builder.field(options.prefix, options.disabled);
        }
       return builder.toString();
    }
}
