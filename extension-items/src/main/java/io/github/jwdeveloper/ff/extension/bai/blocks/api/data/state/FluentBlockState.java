package io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state;

import lombok.Data;
import org.bukkit.Material;

@Data
public class FluentBlockState {

    private int index;
    private String name;
    private Material material;
    private int customModelId;
}
