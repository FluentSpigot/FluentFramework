package io.github.jwdeveloper.ff.extension.bai.items.api.schema;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface FluentItemScheamFactory
{
    /**
     *
     * @param name identifier name of item
     * @return item builder
     */
    FluentItemSchemaBuilder create(String name);


    /**
     *
     * @param section section that contains information about item
     * @return ItemBuilder with loaded data from configuration section
     */
    FluentItemSchemaBuilder loadFromConfig(String name, ConfigurationSection section);

    /**
     *
     * @param yamlConfig takes yaml configuration data in form of string
     * @return ItemBuilder with loaded data from yamlConfig
     */
    List<FluentItemSchemaBuilder> loadFromConfig(String yamlConfig);

    /**
     *
     * @param yamlConfig takes yaml configuration data in form of string
     * @return ItemBuilder with loaded data from yamlConfig
     */
    List<FluentItemSchemaBuilder> loadFromConfig(ConfigurationSection yamlConfig);


}
