package io.github.jwdeveloper.ff.extension.items.api;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.items.api.config.FluentItemApiSettings;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public interface FluentItemApi {

    /**
     * @return settings object that contains namespaces
     */
    FluentItemApiSettings settings();

    /**
     * Removes all loaded items and craftings
     */
    void reset();


    /**
     * Finds and register items from config
     *
     * @param section
     */
    FluentItemLoader addItems(ConfigurationSection section);

    /**
     * create new item in code
     *
     * @return
     */
    FluentItemBuilder addItem();


    /**
     * @return list of schemas that are use create fluent item
     */
    List<FluentItem> findItems();


    /**
     * @param tag item tag
     * @return list of items searched by tag
     */
    List<FluentItem> findItemsByTag(String tag);


    /**
     * returns fluent item by its name
     *
     * @return
     */
    ActionResult<FluentItemStack> fromItemStack(ItemStack itemStack);


    /**
     * @param uniqueName unique name
     * @return
     */
    Optional<FluentItem> findFluentItem(String uniqueName);


}
