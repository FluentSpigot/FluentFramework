package io.github.jwdeveloper.ff.extension.files.api.fluent_files.repository;


import io.github.jwdeveloper.ff.core.common.java.ObjectUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import lombok.Data;
import org.bukkit.Material;

import java.util.Optional;
import java.util.UUID;

@Data
public abstract class DataModel {
    private UUID uuid = UUID.randomUUID();
    private String name = "";
    private String description = "";
    private Material icon = Material.DIRT;

    public boolean isNull() {
        return uuid == null;
    }


    public <T extends DataModel> Optional<T> copy() {
        try {

            var result = (T) ObjectUtils.copyObjectDeep(this,getClass());
            return Optional.of(result);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Can not copy object for class " + this.getClass().getSimpleName(), e);
        }
        return Optional.empty();
    }
}
