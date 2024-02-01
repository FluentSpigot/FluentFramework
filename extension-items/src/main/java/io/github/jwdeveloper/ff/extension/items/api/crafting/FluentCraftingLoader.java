package io.github.jwdeveloper.ff.extension.items.api.crafting;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.function.Consumer;

public interface FluentCraftingLoader
{
    FluentCraftingLoader fromFluentItem(FluentItem section);

    FluentCraftingLoader fromSection(ConfigurationSection section);

    FluentCraftingLoader fromBuilder(Consumer<FluentCraftingBuilder> builderConsumer);

    FluentCrafting register();
}
