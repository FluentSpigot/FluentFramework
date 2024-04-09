package io.github.jwdeveloper.ff.extension.bai.blocks.api.data.drop;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class FluentBlockDrop {
    private ItemStack itemStack;
    private float probability;
}
