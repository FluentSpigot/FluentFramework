package io.github.jwdeveloper.ff.extension.items.api.schema;

import org.bukkit.Color;
import org.bukkit.Material;

import java.util.List;

public interface FluentItemSchemaBuilder {
    FluentItemSchemaBuilder withName(String name);

    FluentItemSchemaBuilder withDisplayName(String displayName);

    FluentItemSchemaBuilder withMaterial(Material material);

    FluentItemSchemaBuilder withLore(String... lore);

    public FluentItemSchemaBuilder withPermission(String permission);

    FluentItemSchemaBuilder addProperty(String key, Object value);
    FluentItemSchemaBuilder addPropertyIfNotExists(String key, Object value);

    FluentItemSchemaBuilder withCustomModelId(int id);

    FluentItemSchemaBuilder withColor(String hexColor);

    FluentItemSchemaBuilder withColor(int r, int g, int b);

    FluentItemSchemaBuilder withColor(Color color);

    FluentItemSchemaBuilder withTag(String tag);

    FluentItemSchemaBuilder withStackable(boolean isStackable);

    FluentItemSchemaBuilder withCrafting(List<String> crafting);

    FluentItemSchema build();
}
