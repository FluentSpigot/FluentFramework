package io.github.jwdeveloper.ff.extension.bai.craftings.api;

import io.github.jwdeveloper.ff.extension.bai.craftings.api.builder.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Consumer;

public interface FluentCraftingLoader
{
    FluentCraftingLoader fromFluentItem(FluentItem section);

    FluentCraftingLoader fromSection(ConfigurationSection section);

    FluentCraftingLoader fromBuilder(Consumer<FluentCraftingBuilder> builderConsumer);

    FluentCrafting register();
}
