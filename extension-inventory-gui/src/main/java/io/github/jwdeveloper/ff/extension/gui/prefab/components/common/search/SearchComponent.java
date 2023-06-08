package io.github.jwdeveloper.ff.extension.gui.prefab.components.common.search;

import io.github.jwdeveloper.ff.core.spigot.events.FluentEvent;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.core.spigot.tasks.FluentTask;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.events.ButtonClickEvent;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.function.Consumer;

public class SearchComponent implements InventoryComponent {
    private final EventGroup<SearchGuiEvent> onSearchEvent  = new EventGroup<>();
    private final EventGroup<ButtonClickEvent> onSearchReset  = new EventGroup<>();
    private SearchFilter currentFilter;

    @Setter
    private String SearchInfoMessage;

    @Override
    public void onInitialization(InventoryDecorator decorator) {
        decorator.withButton(builder ->
        {
            builder.withTitle("Search");
            builder.withPosition(0, 1);
            builder.withMaterial(Material.SPYGLASS);
            builder.withOnLeftClick(this::onSearchHandler);
            builder.withOnRightClick(event ->
            {
                onSearchReset.invoke(event);
            });
            var a = currentFilter != null;
            builder.withSetDescription("IS filter set "+a);
        });
    }

    public <T> void addSearchFilter(SearchFilter<T> filter) {
        var searchFilter = new SearchFilter<T>() {
            public List<T> onFilter(List<T> content, String query) {
                return filter.onFilter(content, query);
            }
        };
        currentFilter = searchFilter;
    }

    public void onSearch(Consumer<SearchGuiEvent> event) {
        onSearchEvent.subscribe(event);
    }

    public void onReset(Consumer<ButtonClickEvent> event) {
        onSearchReset.subscribe(event);
    }

    private void onSearchHandler(ButtonClickEvent event) {
        event.getPlayer().sendMessage(SearchInfoMessage);
        event.getInventory().close();
        FluentEvent.onEvent(AsyncPlayerChatEvent.class, chatEvent ->
        {
            FluentTask.getManager().task(() ->
            {
                if (!chatEvent.getPlayer().equals(event.getPlayer())) {
                    return;
                }
                var searchEvent = new SearchGuiEvent(
                        event.getPlayer(),
                        event.getInventory(),
                        event.getButton(),
                        chatEvent.getMessage(),
                        currentFilter);
                onSearchEvent.invoke(searchEvent);
                event.getInventory().open(event.getPlayer());
            });
        });
    }
}
