package io.github.jwdeveloper.ff.extension.gui.prefab.observers.list;

import io.github.jwdeveloper.ff.extension.gui.prefab.observers.NotifierOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.observers.events.onSelectEvent;
import lombok.Setter;

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
