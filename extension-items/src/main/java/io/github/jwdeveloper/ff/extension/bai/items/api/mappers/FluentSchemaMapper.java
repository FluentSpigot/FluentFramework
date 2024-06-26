package io.github.jwdeveloper.ff.extension.bai.items.api.mappers;

import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import org.bukkit.configuration.ConfigurationSection;

public interface FluentSchemaMapper
{
    FluentItemSchema map(String name, ConfigurationSection section);
}
