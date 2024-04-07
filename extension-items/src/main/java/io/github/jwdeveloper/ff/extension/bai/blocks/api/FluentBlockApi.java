package io.github.jwdeveloper.ff.extension.bai.blocks.api;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemLoader;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public interface FluentBlockApi {
    /**
     * Finds and register items from config
     *
     * @param section
     */
    FluentItemLoader createBlockBehaviour(ConfigurationSection section);

    /**
     * create new item in code
     *
     * @return
     */
    BlockBuilder createBlockBehaviour();


    /**
     * @return list of schemas that are use create fluent item
     */
    List<FluentBlock> findBlocks();


    /**
     * @param tag item tag
     * @return list of items searched by tag
     */
    List<FluentBlock> findByTag(String tag);


    /**
     * returns fluent item by its name
     *
     * @return
     */
    ActionResult<FluentBlockInstance> fromMinecraftBlock(Block itemStack);

    ActionResult<FluentBlock> fromItemStack(ItemStack itemStack);

    ActionResult<FluentBlock> fromFluentItem(FluentItem fluentItem);


    /**
     * @param uniqueName unique name
     * @return
     */
    Optional<FluentBlock> findFluentBlock(String uniqueName);
}
