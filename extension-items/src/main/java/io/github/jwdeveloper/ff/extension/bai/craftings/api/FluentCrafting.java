package io.github.jwdeveloper.ff.extension.bai.craftings.api;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.craftings.impl.CraftingOutputAction;
import lombok.Data;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Data
public class FluentCrafting {

    private final Map<Integer, Material> slotsMaterials = new HashMap<>();

    private final Map<Integer, Function<ItemStack, Boolean>> slotsValidators = new HashMap<>();

    @Setter
    private String identifier;

    @Setter
    private FluentItem fluentItem;

    @Setter
    private CraftingOutputAction craftingOutputAction;

    public FluentCrafting(String craftingName) {
        this.identifier = craftingName;
    }

    public ItemStack getOutput(ItemStack[] input) {
        if (craftingOutputAction == null) {
            throw new RuntimeException("Crafting output action should not be null! " + identifier);
        }

        return craftingOutputAction.onOutput(this, input);
    }


}
