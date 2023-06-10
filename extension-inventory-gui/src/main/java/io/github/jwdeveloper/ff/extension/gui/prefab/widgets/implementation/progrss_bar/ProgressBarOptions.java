package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.WidgetOptions;
import lombok.Setter;
import org.bukkit.ChatColor;

public class ProgressBarOptions extends WidgetOptions
{
    @Setter
    int yield = 10;

    @Setter
    int minimum = 0;

    @Setter
    int maximum = 100;

    @Setter
    String prefix;

    @Setter
    String stepSymbol;

    @Setter
    ChatColor color;

    @Setter
    Observer<Integer> valueObserver;

}
