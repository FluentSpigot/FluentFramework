package io.github.jwdeveloper.ff.extension.bai.items.impl.mappers;

import io.github.jwdeveloper.ff.core.common.ColorUtility;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.extension.bai.items.api.mappers.FluentSchemaMapper;
import io.github.jwdeveloper.ff.extension.bai.items.impl.mappers.utils.ConfigSearcher;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ScheamMapper implements FluentSchemaMapper {
    private final MaterialMapper mapper;

    public ScheamMapper() {
        this.mapper = new MaterialMapper();
    }

    @Override
    public FluentItemSchema map(String name, ConfigurationSection section) {

        var configSearcher = new ConfigSearcher(section);

        var data = new FluentItemSchema();

        data.setName(name);
        configSearcher.findOrDefault(data::setCustomModelId, 0, "custom_model_data", "model-id", "custom-id", "material-id", "item-id");
        configSearcher.findOrDefault(data::setPermission, StringUtils.EMPTY, "permission");
        configSearcher.findOrDefault(data::setStackable, true, "stackable");
        configSearcher.findOrDefault(data::setUnbreakable, true, "unbreakable");
        configSearcher.findOrDefault(data::setRenameable, true, "renameable");
        configSearcher.findOrDefault(data::setDurability, -1, "durability");
        configSearcher.findOrDefault(data::setCategory, StringUtils.EMPTY, "category");
        configSearcher.findOrDefault(data::setTag, StringUtils.EMPTY, "tag");
        configSearcher.findOrDefault(craftings ->
        {
            data.setCrafting(craftings);
        }, new ArrayList<String>(), "crafting");
        configSearcher.findOrDefault(memorySection ->
        {
            var craftingMap = memorySection
                    .getKeys(false)
                    .stream()
                    .collect(Collectors.toMap(e -> e, e -> memorySection.get(e).toString()));

            data.setCraftingMap(craftingMap);
        }, ((MemorySection) new YamlConfiguration()), "crafting-map");

        configSearcher.findOrDefault(displayName ->
        {
            displayName = ColorUtility.toSpigotColors(displayName);
            data.setDisplayName(displayName);
        }, StringUtils.EMPTY, "name");

        configSearcher.findOrDefault(lore ->
        {
            data.setLore(lore.stream().map(e -> ChatColor.WHITE + ColorUtility.toSpigotColors(e)).toList());
        }, new ArrayList<String>(), "lore");

        configSearcher.findOrDefault((materialName) ->
        {
            data.setMaterial(mapper.map(materialName));
        }, "STICK", "item", "material");

        configSearcher.findOrDefault((colorCode) ->
        {
            data.setColor(ColorUtility.fromHex(colorCode));
        }, "#fffff", "color", "dye");

        data.setProperties(configSearcher.getNotSearchedValues());
        return data;
    }


}
