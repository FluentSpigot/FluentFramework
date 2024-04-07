package io.github.jwdeveloper.ff.extension.bai.blocks.api.data;

import lombok.Data;
import org.bukkit.Material;

@Data
public class FluentBlockSchema {

    private Material material = Material.AIR;
    private int customModelId = -1;
    private int hardness = 1;
}
