package io.github.jwdeveloper.ff.extension.gui.OLD.observers.list.checkbox;

import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.extension.gui.OLD.observers.list.FluentListNotifier;
import io.github.jwdeveloper.ff.extension.gui.OLD.observers.list.ListNotifierOptions;
import io.github.jwdeveloper.ff.extension.gui.OLD.observer_button.observers.ButtonObserverEvent;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.function.Supplier;

public class FluentCheckboxListNotifier extends FluentListNotifier<CheckBox> {

    public FluentCheckboxListNotifier(Supplier<List<CheckBox>> supplier, ListNotifierOptions<CheckBox> options) {
        super(supplier, options);
    }

    @Override
    protected void onInitialize(ButtonObserverEvent<CheckBox> event) {
        super.onInitialize(event);
        options.setUnselectedPrefix("  ");
        options.setOnNameMapping(checkBox1 ->
        {
            var value = checkBox1.getObserver().get() ? ChatColor.GREEN + Emoticons.yes : ChatColor.RED + Emoticons.no;
            var textColor = checkBox1.getObserver().get() ? ChatColor.WHITE : ChatColor.GRAY;
            return ChatColor.AQUA + " [" + value + ChatColor.AQUA + "] " + textColor + checkBox1.getName();
        });
    }
}
