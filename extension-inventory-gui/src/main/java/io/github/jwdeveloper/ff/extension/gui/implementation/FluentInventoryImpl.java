package io.github.jwdeveloper.ff.extension.gui.implementation;

import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.core.api.enums.InventoryState;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.ChildrenManager;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.buttons.ButtonManager;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.events.*;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.permissions.PermissionManager;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.InventoryDecoratorImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.ButtonManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import io.github.jwdeveloper.ff.extension.gui.implementation.events.SpigotListenerActionEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FluentInventoryImpl implements FluentInventory {
    @Getter
    private Player player;


    private final InventorySettings inventorySettings;
    private final ChildrenManager children;
    private final ButtonManagerImpl buttonManager;
    private final EventsManager events;
    private final PermissionManager permission;
    private final SimpleLogger logger;

    private Object[] lastArgument;

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
        logger.setPrefix("Inventory");
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
        logger.info("Open Inventory for handle", inventorySettings.getHandle(),player.getName());
        player.openInventory(inventorySettings.getHandle());
        inventorySettings.setState(InventoryState.OPEN);
        lastArgument = args;
    }

    @Override
    public void close() {
        if(!doOnCloseEvent())
        {
            open(player, lastArgument);
            return;
        }


        inventorySettings.setState(InventoryState.CLOSED);
        player.closeInventory();
        logger.info("Close Inventory for handle", inventorySettings.getHandle(),player.getName());
    }

    @Override
    public void refresh() {
        buttonManager.refresh();
        logger.info("Refresh Inventory for handle", inventorySettings.getHandle(),player.getName());
    }

    @Override
    public boolean click(InventoryClickEvent event) {
        try {
            event.setCancelled(true);
            logger.info("Click event begin");
            if (event.getSlot() > inventorySettings.getSlots()) {
                if (!doOnClickPlayerInventoryEvent(player, event.getCurrentItem())) {
                    return false;
                }
                logger.info("doOnClickPlayerInventoryEvent");
                return true;
            }

            var button = buttonManager.getButton(event.getSlot());
            if (button == null || !button.isActive())
            {
                logger.warning("Click event canceled, button inactive");
                return false;
            }


            if (!permissions().validatePlayer(player, button.getPermissions()))
            {
                logger.warning("Click event canceled, player is not valid");
                return false;
            }


            if (button.hasSound())
                player.playSound(player.getLocation(), button.getSound(), 1, 1);

            if (!doOnClickEvent(player, button))
            {
                logger.warning("Click event canceled, doOnClickEvent canceled");
                return false;
            }


            switch (event.getClick()) {
                case SHIFT_LEFT, SHIFT_RIGHT -> {
                    button.doShiftClick(player, this);
                }
                case RIGHT -> {
                    button.doRightClick(player, this);
                }
                case LEFT -> {
                    button.doLeftClick(player, this);
                }
            }
            logger.success("Click invoked successfully");
            buttonManager.refresh(button);
            return true;
        } catch (Exception e) {
            logger.error("Error onClick, inventory " + inventorySettings.getTitle() + " by player " + player.getName(), e);
            return false;
        }
    }

    @Override
    public void drag(InventoryDragEvent event) {

    }

    @Override
    public void setTitle(String title) {
        if (!validatePlayer(player)) {
            return;
        }
        unregisterEventsListening();

        inventorySettings.setTitle(title);
        var currentContent = inventorySettings.getHandle().getContents();
        var newHandle = createInventory();
        newHandle.setContents(currentContent);
        inventorySettings.setHandle(newHandle);

        if (inventorySettings.getState() == InventoryState.OPEN) {
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
        var clickEvent = new ClickGuiEvent(player, buttonUI, this);
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
        if (event.isCancelled()) {
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

    private boolean doOnCloseEvent() {
        var event = new CloseGuiEvent(false, this, player);
        events.onClose().invoke(event);
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


    public InventoryState state() {
        return inventorySettings.getState();
    }

    public Inventory handle() {
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

    @Override
    public List<InventoryComponent> components() {
        return null;
    }

    @Override
    public InventorySettings settings() {
        return inventorySettings;
    }

    @Override
    public SimpleLogger logger() {
        return logger;
    }

    public boolean isInactive() {
        return inventorySettings.isInactive();
    }


    private void registerEventsListening() {
        Bukkit.getServer().getPluginManager().callEvent(new SpigotListenerActionEvent(this, SpigotListenerActionEvent.State.Register));
    }

    private void unregisterEventsListening() {
        Bukkit.getServer().getPluginManager().callEvent(new SpigotListenerActionEvent(this, SpigotListenerActionEvent.State.Unregister));
    }
}
