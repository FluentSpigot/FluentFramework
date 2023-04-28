package io.github.jwdeveloper.ff.extension.gui.implementation.observers.list;

import lombok.Setter;
import io.github.jwdeveloper.ff.extension.gui.implementation.observers.NotifierOptions;
import io.github.jwdeveloper.ff.extension.gui.implementation.observers.events.onSelectEvent;

import java.util.function.Consumer;
import java.util.function.Function;

@Setter
public class ListNotifierOptions<T> extends NotifierOptions
{
    private String selectedPrefix;

    private String unselectedPrefix;

    private boolean ignoreRightClick;

    private Function<T,String> onNameMapping;
    private Consumer<onSelectEvent<T>> onSelectionChanged;

    Function<T, String> getOnNameMapping() {
        return onNameMapping;
    }

    String getSelectedPrefix()
    {
        return selectedPrefix;
    }

    String getUnSelectedPrefix()
    {
        return unselectedPrefix;
    }

    boolean isIgnoreRightClick()
    {
        return ignoreRightClick;
    }

    Consumer<onSelectEvent<T>> getOnSelectionChanged()
    {
        return onSelectionChanged;
    }

}
