package io.github.jwdeveloper.ff.extension.gui.implementation;

import io.github.jwdeveloper.ff.extension.gui.core.implementation.InventoryDecoratorImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.ButtonManagerImpl;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.core.api.enums.InventoryState;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.ChildrenManager;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.buttons.ButtonManager;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.events.*;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.permissions.PermissionManager;
import lombok.Getter;
import new_version.implementation.buttons.ButtonUI;
import new_version.implementation.events.SpigotListenerActionEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import core.api.managers.events.*;

public class FluentInventoryImpl implements FluentInventory {
    @Getter
    private Player player;

    private final InventorySettings inventorySettings;
    private final ChildrenManager children;
    private final ButtonManagerImpl buttonManager;
    private final EventsManager events;
    private final PermissionManager permission;
    private final SimpleLogger logger;

    public FluentInventoryImpl(ChildrenManager children,
                               ButtonManager buttons,
                               EventsManager events,
                               PermissionManager permission,
                               InventorySettings inventorySettings,
                               SimpleLogger logger) {
        this.children = children;
        this.buttonManager = (ButtonManagerImpl) buttons;
        this.events = events;
        this.permission = permission;
        this.inventorySettings = inventorySettings;
        this.logger = logger;
    }


    @Override
    public void open(Player player, Object... args) {
        if (!validatePlayer(player))
            return;

        if (!doOnCreateEvent(player))
            return;

        inventorySettings.setState(InventoryState.CREATED);
        if (children.hasParent()) {
            children.getParent().close();
        }

        inventorySettings.setHandle(createInventory());
        if (!doOnOpenEvent(player)) {
            return;
        }
        this.player = player;
        refresh();
        registerEventsListening();
        player.openInventory(inventorySettings.getHandle());
        inventorySettings.setState(InventoryState.OPEN);
        logger.info("Open Inventory for handle",inventorySettings.getHandle());
    }

    @Override
    public void close() {

    }

    @Override
    public void refresh() {
        buttonManager.refresh();
        logger.info("New content loaded for Bukkit inv ", inventorySettings.getHandle());
    }

    @Override
    public boolean click(InventoryClickEvent event) {
        try {

            if (event.getSlot() > inventorySettings.getSlots()) {
                if (!doOnClickPlayerInventoryEvent(player, event.getCurrentItem())) {
                    return false;
                }
                return true;
            }

            var button = buttonManager.getButton(event.getSlot());
            if (button == null || !button.isActive())
                return false;

            if (!permissions().validatePlayer(player, button.getPermissions()))
                return false;

            if (button.hasSound())
                player.playSound(player.getLocation(), button.getSound(), 1, 1);

            if (!doOnClickEvent(player, button))
                return false;

            switch (event.getClick()) {
                case SHIFT_LEFT, SHIFT_RIGHT -> {
                    button.doShiftClick(player);
                }
                case RIGHT -> {
                    button.doRightClick(player);
                }
                case LEFT -> {
                    button.doLeftClick(player);
                }
            }
            buttonManager.refresh(button);
            return true;
        } catch (Exception e) {
            logger.error("Error onClick, inventory " + inventorySettings.getTitle() + " by player " + player.getName(), e);
            return false;
        }
    }

    @Override
    public void drag(InventoryDragEvent event)
    {

    }

    @Override
    public void setTitle(String title) {
        if(!validatePlayer(player))
        {
            return;
        }
        unregisterEventsListening();

        inventorySettings.setTitle(title);
        var currentContent = inventorySettings.getHandle().getContents();
        var newHandle = createInventory();
        newHandle.setContents(currentContent);
        inventorySettings.setHandle(newHandle);

        if (inventorySettings.getState() == InventoryState.OPEN)
        {
            player.openInventory(inventorySettings.getHandle());
        }

        registerEventsListening();
        logger.info("Title changed with Bukkit inv ", inventorySettings.getHandle().hashCode());
    }


    private boolean doOnClickPlayerInventoryEvent(Player player, ItemStack itemStack) {
        var clickEvent = new ClickPlayerInventoryEvent(player, itemStack);
        events.onClickPlayerInventory().invoke(clickEvent);
        return !clickEvent.isCancelled();
    }

    private boolean doOnClickEvent(Player player, ButtonUI buttonUI) {
        var clickEvent = new ClickEvent(player, buttonUI, this);
        events.onClick().invoke(clickEvent);
        return !clickEvent.isCancelled();
    }

    private boolean doOnCreateEvent(Player player) {
        if (inventorySettings.getState() != InventoryState.NOT_CREATED) {
            return true;
        }
        var decorator = new InventoryDecoratorImpl(this);
        var event = new CreateGuiEvent(player, decorator);
        events.onCreate().invoke(event);
        if(event.isCancelled())
        {
            return false;
        }
        decorator.apply();
        return true;
    }

    private boolean doOnOpenEvent(Player player) {
        var event = new OpenGuiEvent();
        events.onOpen().invoke(event);
        return !event.isCancelled();
    }

    private boolean validatePlayer(Player player) {
        if (player == null || !player.isOnline()) {
            logger.info("Invalid player", ChatColor.RED);
            return false;
        }
        if (!permission.validatePlayer(player)) {
            logger.info("No permissions to open", ChatColor.RED);
            return false;
        }
        return true;
    }

    private Inventory createInventory() {
        switch (inventorySettings.getInventoryType()) {
            case CHEST:
                return Bukkit.createInventory(player, inventorySettings.getSlots(), inventorySettings.getTitle());
            case WORKBENCH:
                return Bukkit.createInventory(player, inventorySettings.getInventoryType(), inventorySettings.getTitle());
            default:
                logger.warning("Sorry inventory type of " + inventorySettings.getInventoryType() + " is not implemented yet ;<");
        }
        return Bukkit.createInventory(player, inventorySettings.getInventoryType(), inventorySettings.getTitle());
    }


    public InventoryState state()
    {
        return inventorySettings.getState();
    }

    public Inventory handle()
    {
        return inventorySettings.getHandle();
    }

    @Override
    public ChildrenManager children() {
        return children;
    }

    @Override
    public ButtonManager buttons() {
        return buttonManager;
    }

    @Override
    public EventsManager events() {
        return events;
    }

    @Override
    public PermissionManager permissions() {
        return permission;
    }

    public InventorySettings settings() {return inventorySettings;}

    @Override
    public SimpleLogger logger() {
        return logger;
    }

    public boolean isInactive()
    {
        return inventorySettings.isInactive();
    }


    private void registerEventsListening()
    {
        Bukkit.getServer().getPluginManager().callEvent(new SpigotListenerActionEvent(this, SpigotListenerActionEvent.State.Register));
    }

    private void unregisterEventsListening()
    {
        Bukkit.getServer().getPluginManager().callEvent(new SpigotListenerActionEvent(this, SpigotListenerActionEvent.State.Unregister));
    }
}
