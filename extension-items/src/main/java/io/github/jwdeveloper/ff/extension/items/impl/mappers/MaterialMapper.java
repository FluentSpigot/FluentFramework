package io.github.jwdeveloper.ff.extension.items.impl.mappers;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import org.bukkit.Material;

public class MaterialMapper {
    public Material map(String materialName) {
        materialName = materialName.trim().replace(" ", "_").toUpperCase();
        return Material.getMaterial(materialName);
    }
}
