package io.github.jwdeveloper.ff.extension.gui.implementation.observers.ints;

import io.github.jwdeveloper.ff.extension.gui.core.implementation.button.observer_button.observers.ButtonObserverEvent;
import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.gui.inventory.styles.ButtonColorSet;
import io.github.jwdeveloper.ff.extension.gui.inventory.styles.FluentButtonStyle;
import org.bukkit.ChatColor;

public class FluentBarIntNotifier extends FluentIntNotifier
{
    private final ButtonColorSet style;
    public FluentBarIntNotifier(FluentButtonStyle style, FluentTranslator translator, IntNotifierOptions notifierOptions)
    {
        super(translator, notifierOptions);
        this.style = style.getColorSet();
    }

    @Override
    protected void onUpdate(ButtonObserverEvent<Integer> event)
    {
        var max =options.getMaximum();
        var current = event.getValue();

        float percent = current * 1.0f / max;

        var builder = new MessageBuilder();
        builder.space(2);
        for(var i = 0.0f;i<=1;i+=0.035f)
        {
            if(percent <= i)
            {
                builder.text(Emoticons.square,ChatColor.GRAY);
            }
            else
            {
                builder.text(Emoticons.square, ChatColor.AQUA);
            }
        }
        event.getButton().updateDescription(getDescriptionIndex(),builder.toString());
    }
}
