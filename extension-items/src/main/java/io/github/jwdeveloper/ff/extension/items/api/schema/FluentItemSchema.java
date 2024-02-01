package io.github.jwdeveloper.ff.extension.items.api.schema;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import lombok.Data;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FluentItemSchema {
    //#yaml section name
    private String name = StringUtils.EMPTY;

    //name
    private String displayName = StringUtils.EMPTY;
    //material, item
    private Material material = Material.STICK;
    //"custom_model_data", "model-id", "custom-id", "material-id", "item-id"
    private int customModelId = 0;
    //tag
    private String tag = StringUtils.EMPTY;
    //category
    private String category = StringUtils.EMPTY;
    //permission
    private String permission = StringUtils.EMPTY;
    //color, dye
    private Color color = Color.WHITE;

    //stackable
    private boolean stackable = true;
    //unbreakable
    private boolean unbreakable = true;
    //renameable
    private boolean renameable = true;
    //durability
    private int durability = -1;
    //lore
    private List<String> lore = new ArrayList<>();

    //crafting-map
    private Map<String, String> craftingMap = new HashMap<>();
    //crafting
    private List<String> crafting = new ArrayList<>();

    private Map<String, Object> properties = new HashMap<>();

    public FluentItemSchema(String name) {
        this.name = name;
    }

    public FluentItemSchema() {
    }

    public String toString() {
        return JsonUtility.getGson().toJson(this);
    }
}
