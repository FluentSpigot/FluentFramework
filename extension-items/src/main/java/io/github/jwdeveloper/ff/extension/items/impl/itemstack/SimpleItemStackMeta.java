package io.github.jwdeveloper.ff.extension.items.impl.itemstack;

import com.google.gson.reflect.TypeToken;
import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStackMeta;
import lombok.Data;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

@Data
public class SimpleItemStackMeta implements FluentItemStackMeta {



    private String uniqueId;

    private String uniqueName;

    private String tag;

    private String pluginVersion;

    private String pluginSession;
}
