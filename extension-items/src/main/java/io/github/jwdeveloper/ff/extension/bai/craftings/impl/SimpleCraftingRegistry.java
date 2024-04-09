package io.github.jwdeveloper.ff.extension.bai.craftings.impl;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCrafting;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SimpleCraftingRegistry implements FluentCraftingRegistry {
    private Map<Material, Set<FluentCrafting>>[] craftingMaterialIndexes;
    private Map<String, FluentCrafting> craftingNameIndexes;

    public SimpleCraftingRegistry() {
        reset();
    }

    @Override
    public void reset() {
        this.craftingNameIndexes = new HashMap<>();
        this.craftingMaterialIndexes = new Map[9];
        for (var i = 0; i < 9; i++) {
            this.craftingMaterialIndexes[i] = new HashMap<Material, Set<FluentCrafting>>();
        }
    }

    @Override
    public List<FluentCrafting> findAll() {
        var resultList = new ArrayList<FluentCrafting>();
        for (var materialSetMap : craftingMaterialIndexes) {
            if (materialSetMap != null) {
                for (var craftingSet : materialSetMap.values()) {
                    resultList.addAll(craftingSet);
                }
            }
        }
        return resultList;
    }

    @Override
    public Optional<FluentCrafting> findByFluentItem(FluentItem fluentItem) {
        return craftingNameIndexes.values()
                .stream()
                .filter(e -> fluentItem.equals(e.getFluentItem()))
                .findFirst();
    }


    @Override
    public Optional<FluentCrafting> findByName(String uniqueName) {
        return Optional.empty();
    }

    @Override
    public List<FluentCrafting> findByTag(String tag) {
        return null;
    }

    @Override
    public void register(FluentCrafting fluentCrafting) {

        craftingNameIndexes.put(fluentCrafting.getIdentifier(), fluentCrafting);
        for (var entry : fluentCrafting.getSlotsMaterials().entrySet()) {
            var slot = entry.getKey();
            var material = entry.getValue();
            craftingMaterialIndexes[slot].computeIfAbsent(material, material1 -> new HashSet<>());
            craftingMaterialIndexes[slot].get(material).add(fluentCrafting);
        }
        FluentLogger.LOGGER.info("Crafting registered!",fluentCrafting.getIdentifier());
    }

    @Override
    public void unregister(FluentCrafting fluentCrafting) {
        craftingNameIndexes.remove(fluentCrafting.getIdentifier());
        for (var entry : fluentCrafting.getSlotsMaterials().entrySet()) {
            var slot = entry.getKey();
            var material = entry.getValue();
            if (!craftingMaterialIndexes[slot].containsKey(material)) {
                continue;
            }
            var materialMap = craftingMaterialIndexes[slot].get(material);
            materialMap.remove(fluentCrafting);
        }
    }

    public List<FluentCrafting> findMatch(ItemStack[] matrix) {
        Set<FluentCrafting> output = new HashSet<>();
        for (var i = 0; i < matrix.length; i++) {
            if (matrix[i] == null) {
                matrix[i] = new ItemStack(Material.AIR);
            }
            var material = matrix[i] == null ? Material.AIR : matrix[i].getType();
            if (material.isAir()) {
                continue;
            }
            var craftings = craftingMaterialIndexes[i].getOrDefault(material, new HashSet<>());

            if (output.isEmpty()) {
                output.addAll(craftings);
            } else {
                output.retainAll(craftings);
            }

        }
        return output.stream()
                .filter(e ->
                {
                    return isCraftingValid(matrix, e);
                })
                .toList();
    }


    private boolean isCraftingValid(ItemStack[] matrix, FluentCrafting crafting) {


        if (!crafting.getSlotsMaterials().isEmpty()) {
            for (var entry : crafting.getSlotsMaterials().entrySet()) {
                var slot = entry.getKey();
                var material = entry.getValue();
                if (!Objects.equals(material, matrix[slot].getType())) {
                    return false;
                }
            }
        }

        if (!crafting.getSlotsValidators().isEmpty()) {
            for (var entry : crafting.getSlotsValidators().entrySet()) {
                var slot = entry.getKey();
                var item = matrix[slot];
                if (item == null) {
                    continue;
                }
                var validator = entry.getValue();
                if (!validator.apply(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Optional<FluentCrafting> findFirstMatch(ItemStack[] matrix) {
        return findMatch(matrix).stream().findFirst();
    }


}
