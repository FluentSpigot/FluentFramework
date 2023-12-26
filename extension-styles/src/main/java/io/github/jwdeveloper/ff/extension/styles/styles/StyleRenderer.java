package io.github.jwdeveloper.ff.extension.styles.styles;


import org.bukkit.inventory.ItemStack;

public interface StyleRenderer {
    void render(ItemStack buttonUI, StyleRendererOptions options);
}
