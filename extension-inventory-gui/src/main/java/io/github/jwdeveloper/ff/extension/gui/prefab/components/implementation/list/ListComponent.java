package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiClickEvent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.BorderComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.ExitButtonComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.pagination.ButtonMapping;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.pagination.PaginationComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.search.SearchComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.search.SearchFilter;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.title.TitleComponent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListComponent<T> implements InventoryComponent {

    private final EventGroup<GuiClickEvent> contentClickEvent = new EventGroup<>();
    @Getter
    private PaginationComponent<T> pagination;
    @Getter
    private BorderComponent border;
    @Getter
    private TitleComponent title;
    @Getter
    private SearchComponent search;
    @Getter
    private ExitButtonComponent exitButton;

    private Supplier<List<T>> contentSource;


    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {

        if(contentSource == null)
        {
            contentSource = () -> new ArrayList<>();
        }

        decorator.withEvents(e -> e.onClick(this::onContentClickHandler));

        pagination = decorator.withComponent(new PaginationComponent<T>());
        border = decorator.withComponent(new BorderComponent());
        exitButton = decorator.withComponent(new ExitButtonComponent());
        title = decorator.withComponent(new TitleComponent());
        search = decorator.withComponent(new SearchComponent());


        search.onSearch(event ->
        {
            pagination.setContentSource(() -> event.getFilter().onFilter(contentSource.get(), event.getQuery()));
        });
        search.onReset(event ->
        {
            pagination.setContentSource(contentSource);
        });
    }

    public void setContentSource(Supplier<List<T>> contentSource) {
        this.contentSource = contentSource;
        pagination.setContentSource(contentSource);
    }

    public void setContentMapping(ButtonMapping<T> contentMapping) {
        pagination.setContentMapping(contentMapping);
    }

    public void addSearchFilter(String name, SearchFilter<T> searchFilter) {
        getSearch().addSearchFilter(name, searchFilter);
    }

    public void onContentClick(Consumer<GuiClickEvent> event) {
        contentClickEvent.subscribe(event);
    }

    public EventGroup<GuiClickEvent> onContentClick() {
        return contentClickEvent;
    }

    private void onContentClickHandler(GuiClickEvent event) {
        if (!event.getButton().hasTag(PaginationComponent.CONTENT_BUTTON_TAG)) {
            return;
        }
        contentClickEvent.invoke(event);
    }

}
