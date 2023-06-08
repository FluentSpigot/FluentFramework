package io.github.jwdeveloper.ff.extension.gui.prefab.components.list.data_grid;

import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.list.ListComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.events.ClickGuiEvent;
import io.github.jwdeveloper.ff.extension.gui.api.events.CreateGuiEvent;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DataGridComponent<T> extends ListComponent<T> {

    private final static String TITLE_TAG = "data-grid-info";
    private final ObserverBag<DataGridAction> stateObserver = new ObserverBag<DataGridAction>(DataGridAction.NONE);
    private final Map<ButtonRef, DataGridActionButton> actionButtons = new LinkedHashMap<>();
    private final Map<DataGridAction, EventGroup<ButtonClickEvent>> eventGroupMap = new HashMap<>();

    private final ButtonRef cancelButton = new ButtonRef();
    private final Material defaultMaterial = Material.GRAY_STAINED_GLASS_PANE;

    @Override
    public void onInitialization(InventoryDecorator decorator) {
        super.onInitialization(decorator);

        decorator.withEvents(e -> e.onCreate(this::onCreateHandler));
        getTitle().addTitleModel(TITLE_TAG, () -> "");
        onContentClick(this::onContentClickHandler);
        decorator.withButton(builder ->
        {
            builder.withMaterial(Material.GOLD_BLOCK);
            builder.withPosition(6, 4);
            builder.withTitle("Cancel");
            builder.withActive(false);
            builder.withReference(cancelButton);
            builder.withOnLeftClick(event ->
            {
                stateObserver.set(DataGridAction.NONE);
            });
        });

        var deleteButton = new DataGridActionButton();
        deleteButton.setIcon(Material.RED_CONCRETE);
        deleteButton.setName("Delete");
        deleteButton.setMessage(ChatColor.DARK_RED + "[DELETE]");
        deleteButton.setState(DataGridAction.DELETE);
        deleteButton.setBorderIcon(Material.RED_STAINED_GLASS_PANE);

        var deleteRef = createActionButton(deleteButton, decorator);


        var createButton = new DataGridActionButton();
        createButton.setIcon(Material.GREEN_CONCRETE);
        createButton.setName("Create");
        createButton.setMessage(ChatColor.DARK_GREEN + "[CREATE]");
        createButton.setState(DataGridAction.CREATE);
        createButton.setBorderIcon(Material.GREEN_STAINED_GLASS_PANE);

        var createRef = createActionButton(createButton, decorator);

        actionButtons.put(deleteRef, deleteButton);
        actionButtons.put(createRef, createButton);

        stateObserver.subscribe(state ->
        {
            if(state == DataGridAction.NONE)
            {
                cancelButton.get().setActive(false);
                getTitle().disableTitleModel(TITLE_TAG);
               getBorder().setBorderMaterial(defaultMaterial);
                return;
            }
        });
    }

    public void onDataGridAction(DataGridAction action, Consumer<ButtonClickEvent> event)
    {
         if(!eventGroupMap.containsKey(action))
         {
             throw new RuntimeException("Unknown state: " + action);
         }
         eventGroupMap.get(action).subscribe(event);
    }

    private void onCreateHandler(CreateGuiEvent event) {

        var i =4;
        for (var entry : actionButtons.entrySet()) {
            var button = entry.getKey().get();
            event.getInventory().buttons().removeButton(button);
            if (entry.getValue().getOnInvoke().isEmpty())
            {
                continue;
            }
            i++;
            button.setPosition(0,i);
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
                cancelButton.get().setActive(true);
                getTitle().enableTitleModel(TITLE_TAG);
                getTitle().setTitleModel(TITLE_TAG, action::getMessage);
                getBorder().setBorderMaterial(action.getBorderIcon());
                stateObserver.set(action.getState());
            });
        });
        eventGroupMap.put(action.getState(), action.getOnInvoke());
        return buttonRef;
    }

    private void onContentClickHandler(ClickGuiEvent event)
    {
        var state = stateObserver.get();
        if(state == DataGridAction.NONE)
        {
            return;
        }
        if (!eventGroupMap.containsKey(state))
        {
            throw new RuntimeException("Unknown state: " + state.name());
        }
        var events = eventGroupMap.get(state);
        events.invoke(new ButtonClickEvent(event.getPlayer(), event.getButton(), event.getInventory()));
        stateObserver.set(DataGridAction.NONE);

    }


}
