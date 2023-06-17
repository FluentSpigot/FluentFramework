package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.data_grid;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import lombok.Data;
import org.bukkit.Material;

@Data
public class DataGridActionButton {
    private DataGridAction action;
    private String name;
    private String message;
    private Material icon;
    private Material borderIcon;
    private EventGroup<ButtonClickEvent> onInvoke = new EventGroup<>();
}
