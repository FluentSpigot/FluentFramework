package io.github.jwdeveloper.ff.extension.bai.items.impl.decorators;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.bai.items.api.decorator.FluentItemDecorator;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemCreateEvent;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.function.Consumer;

public class StatsDecorator implements FluentItemDecorator {
    @Override
    public void onDecorating(FluentItemCreateEvent event) {
        var properties = event.getFluentItem().getSchema().getProperties();
        var meta = event.getItemStack().getItemMeta();
        onProperty("ItemFlags", properties, o ->
        {
            List<String> flags = (List<String>) o;
            flags.stream().map(ItemFlag::valueOf).toList().forEach(itemFlag ->
            {
                meta.addItemFlags(itemFlag);
            });
        });
        onProperty("Enchantments", properties, o ->
        {
            var enchantments = (ConfigurationSection) o;
            for (var key : enchantments.getKeys(false)) {
                var enchantment = Enchantment.getByKey(NamespacedKey.minecraft(key));
                if (enchantment == null) {
                    FluentLogger.LOGGER.info("Enchant not found!", key);
                    continue;
                }
                var level = enchantments.get(key);
                Number value = (Number) level;
                meta.addEnchant(enchantment, value.intValue(), true);
            }
        });
        onProperty("AttributeModifiers", properties, o ->
        {
            var modifiers = (ArrayList<LinkedHashMap<String, Object>>) o;

            for (var modifier : modifiers) {
                var attributeName = modifier.get("attribute");
                var attributeValue = modifier.get("amount");
                var slotName = modifier.get("slot");
                var operationName = modifier.get("operation");


                var slot = EquipmentSlot.valueOf((String) slotName);
                var attribute = Attribute.valueOf((String) attributeName);
                var value = (Number) attributeValue;
                var operation = AttributeModifier.Operation.valueOf((String) operationName);


                var attributeModifier = new AttributeModifier(
                        UUID.randomUUID(),
                        attribute.getKey().getKey(),
                        value.intValue(),
                        operation,
                        slot);

                meta.addAttributeModifier(attribute, attributeModifier);
            }
        });
        onProperty("PotionEffects", properties, o ->
        {
            if (meta instanceof PotionMeta potionMeta) {
                var values = (ArrayList<LinkedHashMap<String, Object>>) o;
                for (var value : values) {
                    var potionEffectTypeName = (String) value.get("type");
                    var duration = (Number) value.get("duration");
                    var amplifier = (Number) value.get("amplifier");
                    var ambient = (boolean) value.get("ambient");
                    var particles = (boolean) value.get("particles");
                    var icon = (boolean) value.get("icon");
                    var type = PotionEffectType.getByKey(NamespacedKey.minecraft(potionEffectTypeName));
                    var effect = new PotionEffect(type, duration.intValue(), amplifier.intValue(), ambient, particles, icon);
                    potionMeta.addCustomEffect(effect, true);
                    potionMeta.setColor(Color.GREEN);
                }
            }
        });
        event.getItemStack().setItemMeta(meta);
    }


    private void onProperty(String name, Map<String, Object> props, Consumer<Object> action) {
        if (props.containsKey(name)) {
            action.accept(props.get(name));
        }
    }
}
