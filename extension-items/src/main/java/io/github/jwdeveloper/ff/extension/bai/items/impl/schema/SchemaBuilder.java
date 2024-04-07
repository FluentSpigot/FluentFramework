package io.github.jwdeveloper.ff.extension.bai.items.impl.schema;

import io.github.jwdeveloper.ff.core.common.ColorUtility;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchemaBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class SchemaBuilder implements FluentItemSchemaBuilder {
    private final FluentItemSchema fluentItemSchema;

    public SchemaBuilder(FluentItemSchema fluentItemData) {
        this.fluentItemSchema = fluentItemData;
    }

    public SchemaBuilder() {
        this.fluentItemSchema = new FluentItemSchema();
    }

    @Override
    public FluentItemSchemaBuilder withName(String name) {
        fluentItemSchema.setName(name);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withDisplayName(String displayName) {
        fluentItemSchema.setDisplayName(displayName);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withMaterial(Material material) {
        fluentItemSchema.setMaterial(material);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withLore(String[] lore) {
        fluentItemSchema.setLore(Arrays.stream(lore).toList());
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withPermission(String permission)
    {
        fluentItemSchema.setPermission(permission);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder addProperty(String key, Object value) {
        fluentItemSchema.getProperties().put(key, value);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder addPropertyIfNotExists(String key, Object value) {

        if (fluentItemSchema.getProperties().containsKey(key)) {
            return this;
        }
        return addProperty(key, value);
    }

    @Override
    public FluentItemSchemaBuilder withCustomModelId(int id) {
        fluentItemSchema.setCustomModelId(id);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withColor(String hexColor) {
        this.fluentItemSchema.setColor(ColorUtility.fromHex(hexColor));
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withColor(int r, int g, int b) {
        this.fluentItemSchema.setColor(ColorUtility.fromRgb(r, g, b));
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withColor(Color color) {
        this.fluentItemSchema.setColor(color);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withTag(String tag) {
        this.fluentItemSchema.setTag(tag);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withIsBlock(boolean isBlock) {
        this.fluentItemSchema.setBlock(isBlock);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withStackable(boolean isStackable) {
        this.fluentItemSchema.setStackable(isStackable);
        return this;
    }

    @Override
    public FluentItemSchemaBuilder withCrafting(List<String> crafting) {
        this.fluentItemSchema.setCrafting(crafting);
        return this;
    }


    @Override
    public FluentItemSchema build() {
        return fluentItemSchema;
    }
}
