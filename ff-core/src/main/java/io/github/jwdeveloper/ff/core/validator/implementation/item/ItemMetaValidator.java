package io.github.jwdeveloper.ff.core.validator.implementation.item;

import io.github.jwdeveloper.ff.core.validator.implementation.ValidatorBase;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ItemMetaValidator extends ValidatorBase<ItemMeta, ItemMetaValidator> {


    public ItemMetaValidator mustHasDisplayedName() {
        return mustComplyRule(ItemMeta::hasDisplayName, "Meta has not displayed name");
    }

    public ItemMetaValidator mustHasDisplayedName(String name) {
        return mustComplyRule(e -> e.hasDisplayName() && e.getDisplayName().equals(name), "Meta has not displayed name or Displayed name is not equal");
    }

    public ItemMetaValidator mustHasNamespaceKey(NamespacedKey namespacedKey) {
        return mustComplyRule(e -> e.getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING), "Namespace key not found");
    }

    public ItemMetaValidator mustHasNamespaceKey(NamespacedKey namespacedKey, String value) {
        return mustHasNamespaceKey(namespacedKey).mustComplyRule(target ->
        {
            var container = target.getPersistentDataContainer();
            var val = container.get(namespacedKey, PersistentDataType.STRING);
            return Objects.equals(val, value);
        }, "Namespace data value is not matching");
    }

    public ItemMetaValidator mustHasCustomModelData(int customModelDataId) {
        return mustComplyRule(e -> e.hasCustomModelData() && e.getCustomModelData() == customModelDataId, "Has different CustomModelData");
    }

    public ItemMetaValidator mustHasLore() {
        return mustComplyRule(ItemMeta::hasLore, "ItemMeta has not lore");
    }

    public ItemMetaValidator mustHasEnchants() {
        return mustComplyRule(ItemMeta::hasEnchants, "ItemMeta has not enchants");
    }

    public ItemMetaValidator mustHasLore(String... lore) {
        return mustComplyRule(e ->
        {
            if (!e.hasLore()) {
                return false;
            }

            var metaLore = e.getLore();
            if (metaLore.size() != lore.length) {
                return false;
            }

            for (var i = 0; i < metaLore.size(); i++) {

                if (!metaLore.get(i).equals(lore[i])) {
                    return false;
                }
            }
            return true;
        }, "ItemMeta has not enchants");
    }
}
