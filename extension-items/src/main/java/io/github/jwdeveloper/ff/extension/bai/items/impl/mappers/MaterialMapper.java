package io.github.jwdeveloper.ff.extension.bai.items.impl.mappers;

import org.bukkit.Material;

public class MaterialMapper {
    public Material map(String materialName) {
        materialName = materialName.trim().replace(" ", "_").toUpperCase();
        return Material.getMaterial(materialName);
    }
}
