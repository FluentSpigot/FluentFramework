package io.github.jwdeveloper.ff.extension.bai.items.impl.mappers;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemRegistry;
import io.github.jwdeveloper.ff.extension.bai.common.FrameworkSettings;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCrafting;
import io.github.jwdeveloper.ff.extension.bai.items.api.mappers.FluentCraftingMapper;
import org.bukkit.Material;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class CraftingMapper implements FluentCraftingMapper {
    private final FluentItemRegistry fluentItemRegistry;
    private final MaterialMapper materialMapper;
    private final FrameworkSettings settings;

    public CraftingMapper(FluentItemRegistry fluentItemRegistry, FrameworkSettings settings) {
        this.settings = settings;
        this.fluentItemRegistry = fluentItemRegistry;
        this.materialMapper = new MaterialMapper();
    }

    @Override
    public ActionResult<FluentCrafting> map(List<String> craftingData, FluentItem target) {
        if (craftingData.size() != 3) {
            return ActionResult.failed("There should be 3 rows for crafting but was: " + craftingData.size());
        }
        var craftingMap = target.getSchema().getCraftingMap();
        var fluentCrafting = new FluentCrafting(target.getSchema().getName());
        fluentCrafting.setFluentItem(target);
        fluentCrafting.setCraftingOutputAction((fluentCrafting1, matrix) ->
        {
            return target.toItemStack();
        });
        for (var row = 0; row < craftingData.size(); row++) {
            var rowOptional = getRowData(craftingData.get(row));
            if (rowOptional.isFailed()) {
                return rowOptional.cast();
            }

            var col = -1;
            for (var itemName : rowOptional.getContent()) {
                col++;
                var index = row * 3 + col;
                var materialname = itemName;
                if (craftingMap.containsKey(itemName)) {
                    materialname = craftingMap.get(itemName).toString();
                }
                var optional = handleSingleSlot(index, materialname, fluentCrafting);
                if (optional.isFailed()) {
                    return optional.cast(fluentCrafting);
                }
            }
        }
        return ActionResult.success(fluentCrafting);
    }

    private ActionResult<String[]> getRowData(String row) {
        var splitSymbol = StringUtils.EMPTY;
        if (row.contains("|")) {
            splitSymbol = "|";
        }
        if (row.contains(",")) {
            splitSymbol = ",";
        }
        if (row.contains("/")) {
            splitSymbol = "/";
        }
        if (row.contains("=")) {
            splitSymbol = "=";
        }
        if (StringUtils.isNullOrEmpty(splitSymbol)) {
            return ActionResult.failed("Crafting row must split slots by  [ | , / = ] character!");
        }
        var itemsNames = row.split(splitSymbol);
        itemsNames = createArrayFromIndexes(itemsNames, 3);

        for (var i = 0; i < itemsNames.length; i++) {
            itemsNames[i] = itemsNames[i].trim();
            if (StringUtils.isNullOrEmpty(itemsNames[i])) {
                itemsNames[i] = Material.AIR.name();
            }
        }

        return ActionResult.success(itemsNames);
    }

    private ActionResult handleSingleSlot(Integer index, String itemName, FluentCrafting fluentCrafting) {
        if (itemName.trim().contains("@")) {
            var tagName = itemName.trim().replace("@", "");
            if (fluentItemRegistry.findByTag(tagName).isEmpty()) {
                FluentLogger.LOGGER.warning("crafting schema use tag: ", itemName, "for slot", index, "but this tag not exists!");
            }
            fluentCrafting.getSlotsValidators().put(index, itemStack ->
            {
                if (!itemStack.hasItemMeta()) {
                    return false;
                }
                var container = itemStack.getItemMeta().getPersistentDataContainer();
                if (!container.has(settings.getTagKey(), PersistentDataType.STRING)) {
                    return false;
                }
                var value = container.get(settings.getTagKey(), PersistentDataType.STRING);
                return tagName.equalsIgnoreCase(value);
            });
            return ActionResult.success();
        }

        var material = materialMapper.map(itemName);
        if (material != null) {
            fluentCrafting.getSlotsMaterials().put(index, material);
            return ActionResult.success();
        }

        var optional = fluentItemRegistry.findByName(itemName.trim());
        if (optional.isEmpty()) {
            return findSimilarMaterialError(itemName);
        }
        var fluentItem = optional.get();
        fluentCrafting.getSlotsValidators().put(index, fluentItem::isItemStack);
        return ActionResult.success();
    }


    public ActionResult findSimilarMaterialError(String materialName) {
        var sb = new TextBuilder();
        sb.textNewLine("Material not found for name " + materialName + "!, did you mean?");
        var materials = Arrays.stream(Material.values()).filter(e -> e.name().contains(materialName)).toList();
        for (var mat : materials) {
            sb.textNewLine("- " + mat.name());
        }
        return ActionResult.failed(sb.toString());
    }

    public static String[] createArrayFromIndexes(String[] inputArray, int indexes) {
        String[] newArray = new String[indexes];
        for (int index = 0; index < indexes; index++) {
            if (index >= 0 && index < inputArray.length) {
                newArray[index] = inputArray[index];
            } else {
                newArray[index] = "";
            }
        }
        return newArray;
    }

}
