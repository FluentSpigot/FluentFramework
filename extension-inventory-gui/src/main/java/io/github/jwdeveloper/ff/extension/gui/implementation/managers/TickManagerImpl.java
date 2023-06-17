package io.github.jwdeveloper.ff.extension.gui.implementation.managers;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiTickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.managers.TickManager;
import org.bukkit.entity.Player;

public class TickManagerImpl implements TickManager {

    private final InventorySettings settings;
    private final FluentTaskManager taskManager;
    private SimpleTaskTimer taskTimer;


    public TickManagerImpl(InventorySettings inventorySettings, FluentTaskManager taskManager) {
        this.settings = inventorySettings;
        this.taskManager = taskManager;
    }

    @Override
    public void start(Player player, FluentInventory inventory, EventGroup<GuiTickEvent> eventGroup) {
        if (taskTimer == null) {
            taskTimer = taskManager.taskTimer(settings.getTicksUpdate(), (iteration, task) ->
            {
                eventGroup.invoke(new GuiTickEvent(inventory, player, iteration));
            });
        }
        if (taskTimer.isRunning()) {
            return;
        }
        taskTimer.reset();
        taskTimer.setSpeed(settings.getTicksUpdate());
        taskTimer.run();
    }

    @Override
    public void stop() {
        if (taskTimer == null) {
            return;
        }
        taskTimer.stop();

    }
}
