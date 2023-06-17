package io.github.jwdeveloper.ff.extension.gui.implementation.buttons;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@Data
public class ButtonData {
    private String title = " ";
    private String tag = StringUtils.EMPTY;
    private List<String> description = new ArrayList<>();
    private Material material = Material.DIRT;
    private Sound sound;
    private Object dataContext;
    private boolean isActive = true;
    private boolean isHighLighted = false;
    private Vector position = new Vector(0, 0, 0);
    private List<String> permissions = new ArrayList<>();
}
