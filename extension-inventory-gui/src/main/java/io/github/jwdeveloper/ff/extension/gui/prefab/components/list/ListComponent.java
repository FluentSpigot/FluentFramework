package io.github.jwdeveloper.ff.extension.gui.prefab.components.list;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.common.BorderComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.common.ExitButtonComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.common.pagination.ButtonMapping;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.common.pagination.PaginationComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.common.search.SearchComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.common.search.SearchFilter;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.common.title.TitleComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.events.ClickGuiEvent;
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListComponent<T> implements InventoryComponent {

    private final EventGroup<ClickGuiEvent> contentClickEvent = new EventGroup<>();
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
    public void onInitialization(InventoryDecorator decorator) {
        decorator.withEvents(e -> e.onClick(this::onContentClickHandler));

        pagination = decorator.withComponent(new PaginationComponent<T>());
        border = decorator.withComponent(new BorderComponent());
        exitButton = decorator.withComponent(new ExitButtonComponent());
        title = decorator.withComponent(new TitleComponent());
        search = decorator.withComponent(new SearchComponent());



        search.onSearch(event ->
        {
            pagination.setContentSource(() -> event.getFilter().onFilter(contentSource.get(), event.getQuery()));
            pagination.refresh();
        });
        search.onReset(event ->
        {
            pagination.setContentSource(contentSource);
            pagination.refresh();
        });
    }

    public void setContentSource(Supplier<List<T>> contentSource) {
        this.contentSource = contentSource;
        pagination.setContentSource(contentSource);
    }

    public void setContentMapping(ButtonMapping<T> contentMapping) {
        pagination.setContentMapping(contentMapping);
    }

    public void addSearchFilter(SearchFilter<T> searchFilter) {
        getSearch().addSearchFilter(searchFilter);
    }

    public void onContentClick(Consumer<ClickGuiEvent> event) {
        contentClickEvent.subscribe(event);
    }

    public EventGroup<ClickGuiEvent> onContentClick() {
        return contentClickEvent;
    }

    private void onContentClickHandler(ClickGuiEvent event) {
        if (!event.getButton().hasTag(PaginationComponent.CONTENT_BUTTON_TAG)) {
            return;
        }
        contentClickEvent.invoke(event);
    }

}
