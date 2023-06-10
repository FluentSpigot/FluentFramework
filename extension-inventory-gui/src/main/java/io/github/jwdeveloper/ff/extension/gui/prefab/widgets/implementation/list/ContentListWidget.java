package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list;

import io.github.jwdeveloper.ff.core.common.java.JavaUtils;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderEvent;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidget;

import java.util.ArrayList;
import java.util.List;

public class ContentListWidget<T> implements ButtonWidget {
    private final ContentListOptions<T> options;
    private int currentIndex;

    private final List<T> EMPTY_ARRAY = new ArrayList<>();

    public ContentListWidget(ContentListOptions<T> options) {
        this.options = options;
    }


    @Override
    public void onCreate(ButtonBuilder builder) {

        JavaUtils.throwIfNull(options.selectedItemObserver, "selectedItemSource must not be null");
        options.itemPrefix = JavaUtils.ifNull(options.itemPrefix, " ");
        options.selectedItemPrefix = JavaUtils.ifNull(options.selectedItemPrefix, "> ");
        options.contentSource = JavaUtils.ifNull(options.contentSource, () -> EMPTY_ARRAY);
        options.contentMapping = JavaUtils.ifNull(options.contentMapping, Object::toString);
        options.leftClickInfo = JavaUtils.ifNull(options.leftClickInfo, "Previous");
        options.rightClickInfo = JavaUtils.ifNull(options.rightClickInfo, "Next");


        builder.withStyleRenderer(style ->
        {
            if(options.isCanRender())
            {
                style.withDescriptionLine(options.getId(), this::onRender);
            }
            style.withLeftClickInfo(options.leftClickInfo);
            style.withRightClickInfo(options.rightClickInfo);
        });
        builder.withOnLeftClick(this::onLeftClick);
        builder.withOnRightClick(this::onRightClick);
    }

    private void onLeftClick(ButtonClickEvent e)
    {
        if(options.disableLeftClick)
        {
            return;
        }

        if (getContent().isEmpty()) {
            return;
        }
        currentIndex = (currentIndex + 1) % getContent().size();
        options.selectedItemObserver.set(getContent().get(currentIndex));
        options.selectionChangedEvent.invoke(createEvent(e));
    }

    private void onRightClick(ButtonClickEvent e) {
        if(options.disableRightClick)
        {
            return;
        }

        if (getContent().isEmpty()) {
            return;
        }
        currentIndex = (currentIndex - 1);
        if (currentIndex < 0) {
            currentIndex = getContent().size() - 1;
        }
        options.selectedItemObserver.set(getContent().get(currentIndex));
        options.selectionChangedEvent.invoke(createEvent(e));
    }

    private String onRender(StyleRenderEvent e)
    {
        if(options.showOnlySelectedItem)
        {
            var selected = options.selectedItemObserver.get();
            return e.builder()
                    .text(options.selectedItemPrefix)
                    .text(options.contentMapping.apply(selected))
                    .toString();
        }


        T value = null;
        var builder = e.builder();
        var content = getContent();
        currentIndex = findCurrentIndex();

        for (var i = 0; i < content.size(); i++) {
            value = content.get(i);
            var msg = currentIndex == i ? builder.text(options.selectedItemPrefix) : builder.text(options.itemPrefix);
            builder.text(options.contentMapping.apply(value));
            builder.newLine();
        }
        return builder.toString();
    }





    private int findCurrentIndex() {
        var item = options.selectedItemObserver.get();
        if (item == null) {
            return 0;
        }
        var values = getContent();
        for (var i = 0; i < values.size(); i++) {
            if (item.equals(values.get(i))) {
                return i;
            }
        }
        return 0;
    }

    private List<T> getContent() {
        var content = options.contentSource.get();
        return content == null ? EMPTY_ARRAY : content;
    }


    private ContentSelectionEvent<T> createEvent(ButtonClickEvent event) {
        return new ContentSelectionEvent<T>(
                event.getButton(),
                options.selectedItemObserver.get(),
                getContent(),
                currentIndex, event.getPlayer(),
                event.getInventory());
    }
}
