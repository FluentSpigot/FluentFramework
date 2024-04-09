package io.github.jwdeveloper.ff.extension.bai.items.impl.schema;

import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchemaBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemScheamFactory;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.extension.bai.items.api.mappers.FluentSchemaMapper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchemaFactory implements FluentItemScheamFactory {
    private final Map<String, FluentSchemaMapper> mappers;

    public SchemaFactory(Map<String, FluentSchemaMapper> mappers) {
        this.mappers = mappers;
    }

    @Override
    public FluentItemSchemaBuilder create(String name) {
        return new SchemaBuilder(new FluentItemSchema(name));
    }

    @Override
    public FluentItemSchemaBuilder loadFromConfig(String itemName, ConfigurationSection section) {
        var mapper = mappers.get(itemName);
        if (mapper == null) {
            mapper = mappers.get("DEFAULT");
        }
        var itemData = mapper.map(itemName, section);
        return new SchemaBuilder(itemData);
    }

    @Override
    public List<FluentItemSchemaBuilder> loadFromConfig(String yamlContent) {
        try {
            var yamlConfig = new YamlConfiguration();
            yamlConfig.loadFromString(yamlContent);
            return loadFromConfig(yamlConfig);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load yamlContent", e);
        }
    }

    @Override
    public List<FluentItemSchemaBuilder> loadFromConfig(ConfigurationSection yamlConfig) {
        var builders = new ArrayList<FluentItemSchemaBuilder>();
        for (var key : yamlConfig.getKeys(false)) {
            if (!yamlConfig.isConfigurationSection(key)) {
                continue;
            }
            var section = yamlConfig.getConfigurationSection(key);
            var builder = loadFromConfig(key, section);
            builders.add(builder);
        }
        return builders;
    }


}
