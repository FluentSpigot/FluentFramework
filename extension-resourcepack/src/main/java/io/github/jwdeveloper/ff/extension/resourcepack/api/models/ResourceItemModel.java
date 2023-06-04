package io.github.jwdeveloper.ff.extension.resourcepack.api.models;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


public record ResourceItemModel(int id, String name, Material material) {

    public ItemStack toItemStack() {
        return toItemStack(Color.WHITE);
    }
    public ItemStack toItemStack(Color color) {
        var itemStack = new ItemStack(material());
        var meta = itemStack.getItemMeta();
        if(meta == null)
        {
            return itemStack;
        }
        meta.setDisplayName(name);
        meta.setCustomModelData(id);
        if(meta instanceof LeatherArmorMeta leatherArmorMeta)
        {
            leatherArmorMeta.setColor(color);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}