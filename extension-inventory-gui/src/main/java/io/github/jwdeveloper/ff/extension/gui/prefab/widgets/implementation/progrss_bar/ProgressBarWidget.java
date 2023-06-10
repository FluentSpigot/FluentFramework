package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar;

import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderEvent;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidget;
import org.bukkit.ChatColor;

public class ProgressBarWidget implements ButtonWidget {

    private final ProgressBarOptions options;

    public ProgressBarWidget(ProgressBarOptions options) {
        this.options = options;
    }

    @Override
    public void onCreate(ButtonBuilder builder)
    {
        builder.withStyleRenderer(render ->
        {
            if(options.isCanRender())
            {
                render.withDescriptionLine(options.getId(), this::onRender);
            }
            render.withLeftClickInfo("Increase value by + "+options.yield);
            render.withRightClickInfo("Decrease value by - "+options.yield);
        });
        builder.withOnLeftClick(this::onLeftClick);
        builder.withOnRightClick(this::onRightClick);
    }

    private void onLeftClick(ButtonClickEvent event)
    {
        var value = options.valueObserver.get();
        if (value + options.yield > options.maximum)
        {
            options.valueObserver.set(options.maximum);
            return;
        }
        options.valueObserver.set(value + options.yield);
    }

    private void onRightClick(ButtonClickEvent event)
    {
        var value = options.valueObserver.get();
        if (value - options.yield < options.minimum)
        {
            options.valueObserver.set(options.minimum);
            return;
        }
        options.valueObserver.set(value - options.yield);
    }


    private String onRender(StyleRenderEvent event)
    {
        var max =options.maximum;
        var current =  options.valueObserver.get();
        float percent = current * 1.0f / max;
        var builder = event.builder();
        builder.space(2);
        for(var i = 0.0f;i<=1;i+=0.035f)
        {
            if(percent <= i)
            {
                builder.text(ChatColor.GRAY).text(Emoticons.square);
            }
            else
            {
                builder.text(event.pallet().getPrimary()).text(Emoticons.square);
            }
        }
        return builder.toString();
    }


}
