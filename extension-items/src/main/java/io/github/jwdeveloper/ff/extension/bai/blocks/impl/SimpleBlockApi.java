package io.github.jwdeveloper.ff.extension.bai.blocks.impl;

import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockApi;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockRegistry;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder.BlockBehaviourBuilder;
import io.github.jwdeveloper.ff.extension.bai.common.FrameworkSettings;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemLoader;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Display;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public class SimpleBlockApi implements FluentBlockApi {

    private final PluginCache pluginCache;
    private final FluentBlockRegistry registry;
    private final FrameworkSettings settings;

    public SimpleBlockApi(PluginCache pluginCache,
                          FluentBlockRegistry simpleBlockRegistry,
                          FrameworkSettings settings) {
        this.pluginCache = pluginCache;
        this.registry = simpleBlockRegistry;
        this.settings = settings;
    }

    @Override
    public FluentItemLoader createBlockBehaviour(ConfigurationSection section) {
        return null;
    }

    @Override
    public BlockBuilder createBlockBehaviour() {
        return FluentApi.container().findInjection(BlockBehaviourBuilder.class);
    }

    @Override
    public ActionResult<FluentBlockInstance> fromMinecraftBlock(Block block) {

        if (block == null) {
            return ActionResult.failed("null");
        }
        if (!block.getType().equals(settings.getBlockMaterial()))
        {
            return ActionResult.failed("not barrier");
        }
        if (block.getMetadata("custom-block").isEmpty()) {
            return ActionResult.failed("not custom block");
        }

        var cacheId = block.getLocation().toString();
        if(!pluginCache.contains(cacheId))
        {
            //TODO initialize item display!
            return ActionResult.failed("item display is empty");
        }

        var itemDisplay = pluginCache.get(cacheId);
        var blockName = block.getMetadata("custom-block").get(0);
        var fluentBlock = findFluentBlock(blockName.asString());
        if (fluentBlock.isEmpty()) {
            return ActionResult.failed("fluent block not found!");
        }

        var instance = new SimpleBlockInstance(block, (Display) itemDisplay, fluentBlock.get());
        return ActionResult.success(instance);
    }

    @Override
    public ActionResult<FluentBlock> fromItemStack(ItemStack itemStack) {
        var itemsApi = FluentApi.container().findInjection(FluentItemApi.class);
        var actionResult = itemsApi.fromItemStack(itemStack);
        if (actionResult.isFailed()) {
            return actionResult.cast();
        }
        return fromFluentItem(actionResult.getObject().getFluentItem());
    }

    @Override
    public ActionResult<FluentBlock> fromFluentItem(FluentItem fluentItem) {
        var optional = registry.findAll().stream().filter(e -> e.fluentItem().equals(fluentItem)).findFirst();
        if (optional.isEmpty()) {
            return ActionResult.failed("not found!");
        }
        return ActionResult.success(optional.get());
    }

    @Override
    public List<FluentBlock> findBlocks() {
        return registry.findAll();
    }

    @Override
    public List<FluentBlock> findByTag(String tag) {
        return registry.findByTag(tag);
    }

    @Override
    public Optional<FluentBlock> findFluentBlock(String uniqueName) {
        return registry.findByName(uniqueName);
    }
}
