package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.data_grid;

import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiClickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiCreateEvent;
import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.ListComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DataGridComponent<T> extends ListComponent<T> {

    public final static String TITLE_TAG = "data-grid-info";
    private final ObserverBag<DataGridAction> stateObserver = new ObserverBag<DataGridAction>(DataGridAction.NONE);
    private final Map<ButtonRef, DataGridActionButton> actionButtons = new LinkedHashMap<>();
    private final Map<DataGridAction, EventGroup<ButtonClickEvent>> eventGroupMap = new HashMap<>();

    private final ButtonRef cancelButton = new ButtonRef();
    private ButtonRef deleteButton;

    private ButtonRef createButton;
    private final Material defaultMaterial = Material.GRAY_STAINED_GLASS_PANE;

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {
        super.onInitialization(decorator, inventoryApi);

        decorator.withEvents(e -> e.onCreate(this::onCreateHandler));
        getTitle().addTitleModel(TITLE_TAG, () -> "");
        onContentClick(this::onContentClickHandler);
        decorator.withButton(builder ->
        {
            builder.withMaterial(Material.BARRIER);
            builder.withPosition(6, 4);
            builder.withTitle("Cancel");
            builder.withActive(false);
            builder.withReference(cancelButton);
            builder.withOnLeftClick(event ->
            {
                stateObserver.set(DataGridAction.NONE);
            });
        });

        var deleteActionButton = new DataGridActionButton();
        deleteActionButton.setIcon(Material.RED_CONCRETE);
        deleteActionButton.setName("Delete");
        deleteActionButton.setMessage(ChatColor.DARK_RED + "[DELETE]");
        deleteActionButton.setAction(DataGridAction.DELETE);
        deleteActionButton.setBorderIcon(Material.RED_STAINED_GLASS_PANE);

        deleteButton = createActionButton(deleteActionButton, decorator);


        var createActionButton = new DataGridActionButton();
        createActionButton.setIcon(Material.GREEN_CONCRETE);
        createActionButton.setName("Create");
        createActionButton.setMessage(ChatColor.DARK_GREEN + "[CREATE]");
        createActionButton.setAction(DataGridAction.CREATE);
        createActionButton.setBorderIcon(Material.GREEN_STAINED_GLASS_PANE);

        createButton = createActionButton(createActionButton, decorator);

        actionButtons.put(deleteButton, deleteActionButton);
        actionButtons.put(createButton, createActionButton);

        stateObserver.subscribe(state ->
        {
            if (state == DataGridAction.NONE) {
                cancelButton.get().setActive(false);
                getTitle().disableTitleModel(TITLE_TAG);
                getBorder().setBorderMaterial(defaultMaterial);
            }
        });
    }

    public void onDataGridAction(DataGridAction action, Consumer<ButtonClickEvent> event) {
        if (!eventGroupMap.containsKey(action)) {
            eventGroupMap.put(action, new EventGroup<ButtonClickEvent>());
        }

        eventGroupMap.get(action).subscribe(event);
    }

    private void onCreateHandler(GuiCreateEvent event) {

        var i = 4;
        for (var entry : actionButtons.entrySet()) {
            var button = entry.getKey().get();
            event.getInventory().buttons().removeButton(button);
            if (entry.getValue().getOnInvoke().isEmpty()) {
                continue;
            }
            i++;
            button.setPosition(0, i);
            event.getInventory().buttons().addButton(button);
        }
    }

    private ButtonRef createActionButton(DataGridActionButton action, InventoryDecorator decorator) {
        var buttonRef = new ButtonRef();
        decorator.withButton(builder ->
        {
            builder.withReference(buttonRef);
            builder.withMaterial(action.getIcon());
            builder.withTitle(action.getName());
            builder.withOnLeftClick(event ->
            {
                if (action.getAction() == DataGridAction.CREATE) {
                    action.getOnInvoke().invoke(event);
                    return;
                }
                cancelButton.get().setActive(true);
                getTitle().enableTitleModel(TITLE_TAG);
                getTitle().setTitleModel(TITLE_TAG, action::getMessage);
                getBorder().setBorderMaterial(action.getBorderIcon());
                stateObserver.set(action.getAction());
            });
        });
        eventGroupMap.put(action.getAction(), action.getOnInvoke());
        return buttonRef;
    }

    private void onContentClickHandler(GuiClickEvent event) {
        var state = stateObserver.get();
        if (state == DataGridAction.NONE) {
            if (eventGroupMap.containsKey(DataGridAction.SELECT)) {
                eventGroupMap.get(DataGridAction.SELECT).invoke(new ButtonClickEvent(event.getPlayer(), event.getButton(), event.getInventory()));
            }
            return;
        }
        if (!eventGroupMap.containsKey(state)) {
            throw new RuntimeException("Unknown state: " + state.name());
        }
        var events = eventGroupMap.get(state);
        events.invoke(new ButtonClickEvent(event.getPlayer(), event.getButton(), event.getInventory()));
        stateObserver.set(DataGridAction.NONE);

    }


}
