package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.search;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.api.references.InventoryRef;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.bukkit.Material;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SearchComponent implements InventoryComponent {
    private final EventGroup<SearchGuiEvent> onSearchEvent = new EventGroup<>();
    private final EventGroup<ButtonClickEvent> onSearchReset = new EventGroup<>();
    private final InventoryRef inventoryRef = new InventoryRef();
    private final List<SearchFilterModel> filters = new ArrayList<>();
    private final Observer<SearchFilterModel> currentFilterObserver = ObserverBag.createObserver(new SearchFilterModel("empty", null));
    @Setter
    private String searchInfoMessage;

    @Getter
    private final ButtonRef searchButton = new ButtonRef();

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {

        decorator.withInventoryReference(inventoryRef);
        decorator.withButton(builder ->
        {


            inventoryApi.buttons().inputChat(builder, options ->
            {
                options.setOpenMessage(searchInfoMessage);
                options.onChatInput(this::onChatEvent);
                options.setOpenOnLeftClick(false);
                options.setOpenOnRightClick(true);
                options.setCanRender(false);
            });
            inventoryApi.buttons().<SearchFilterModel>contentList(builder, options ->
            {
                options.setContentSource(() -> filters);
                options.setContentMapping(searchFilterModel -> searchFilterModel.name);
                options.setSelectedItemObserver(currentFilterObserver);
                options.setDisableRightClick(true);
            });

            builder.withStyleRenderer(renderer ->
            {
                renderer.withTitle("Search");
                renderer.withLeftClickInfo("Next filter");
                renderer.withRightClickInfo("Search");
                renderer.withShiftClickInfo("Reset");
            });
            builder.withPosition(0, 1);
            builder.withReference(searchButton);
            builder.withMaterial(Material.SPYGLASS);
            builder.withOnShiftClick(event ->
            {
                onSearchReset.invoke(event);
                inventoryRef.get().refresh();
            });
        });
    }

    public <T> void addSearchFilter(String name, SearchFilter<T> filter) {
        filters.add(new SearchFilterModel(name, filter));
    }

    public void onSearch(Consumer<SearchGuiEvent> event) {
        onSearchEvent.subscribe(event);
    }

    public void onReset(Consumer<ButtonClickEvent> event) {
        onSearchReset.subscribe(event);
    }

    private void onChatEvent(AsyncPlayerChatEvent event) {
        var searchEvent = new SearchGuiEvent(
                event.getPlayer(),
                inventoryRef.get(),
                event.getMessage(),
                currentFilterObserver.get().getFilter());
        onSearchEvent.invoke(searchEvent);
        inventoryRef.get().open(event.getPlayer());
    }


    @Value
    public class SearchFilterModel {
        String name;

        SearchFilter filter;
    }
}
