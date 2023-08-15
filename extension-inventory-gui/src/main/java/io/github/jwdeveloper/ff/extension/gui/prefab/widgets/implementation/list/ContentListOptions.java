package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.WidgetOptions;
import lombok.Setter;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ContentListOptions<T> extends WidgetOptions {
    EventGroup<ContentSelectionEvent<T>> selectionChangedEvent = new EventGroup<>();

    @Setter
    Function<T, String> contentMapping;

    @Setter
    String selectedItemPrefix;

    @Setter
    String itemPrefix;

    @Setter
    String leftClickInfo;

    @Setter
    String rightClickInfo;

    @Setter
    boolean showOnlySelectedItem;

    @Setter
    boolean disableRightClick;

    @Setter
    boolean disableLeftClick;

    @Setter
    Supplier<List<T>> contentSource;

    @Setter
    Observer<T> selectedItemObserver;


    public void setOnSelectionChanged(Consumer<ContentSelectionEvent<T>> consumer) {
        selectionChangedEvent.subscribe(consumer);
    }


}
