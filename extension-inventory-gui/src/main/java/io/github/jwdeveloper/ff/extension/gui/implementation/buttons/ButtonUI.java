package io.github.jwdeveloper.ff.extension.gui.implementation.buttons;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.styles.StyleRendererOptionsDecorator;
import io.github.jwdeveloper.ff.extension.styles.styles.StyleRendererOptions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;


public class ButtonUI {
    private final ButtonData buttonData;

    @Getter
    @Setter
    private StyleRendererOptions styleRendererOptions = new StyleRendererOptions();
    private ItemStack itemStack;
    @Getter
    private final EventGroup<ButtonClickEvent> onLeftClick;

    @Getter
    private final EventGroup<ButtonClickEvent> onShiftClick;

    @Getter
    private final EventGroup<ButtonClickEvent> onRightClick;

    @Getter
    private final EventGroup<ButtonClickEvent> onInventoryTick;

    public ButtonUI(Material material) {
        this();
        setMaterial(material);
    }

    public ButtonUI() {
        buttonData = new ButtonData();
        itemStack = new ItemStack(buttonData.getMaterial());
        onLeftClick = new EventGroup<>();
        onRightClick = new EventGroup<>();
        onShiftClick = new EventGroup<>();
        onInventoryTick = new EventGroup<>();
        hideAttributes();
        setTitle(buttonData.getTitle());
    }

    public ButtonUI(ItemStack itemStack) {
        this();
        buttonData.setMaterial(itemStack.getType());
        buttonData.setDescription(itemStack.getItemMeta().getLore());
        buttonData.setTitle(itemStack.getItemMeta().getDisplayName());
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void doLeftClick(Player player, FluentInventory inventory) {
        performClick(onLeftClick, player, inventory);
    }

    public void doRightClick(Player player, FluentInventory inventory) {
        performClick(onRightClick, player, inventory);
    }

    public void doShiftClick(Player player, FluentInventory inventory) {
        performClick(onShiftClick, player, inventory);
    }

    public <T> T getDataContext() {
        if (buttonData.getDataContext() == null)
            throw new RuntimeException("ButtonUI: does not contains dataContext: " + buttonData.getTitle());
        try {
            return (T) buttonData.getDataContext();
        } catch (Exception e) {
            throw new RuntimeException("ButtonUI: " + "Can not cast DataContext value in button " + buttonData.getTitle(), e);
        }
    }

    public void setDataContext(Object object) {
        buttonData.setDataContext(object);
    }

    private void performClick(EventGroup<ButtonClickEvent> event, Player player, FluentInventory inventory) {
        event.invoke(new ButtonClickEvent(player, this, inventory));
    }

    public boolean isActive() {
        return buttonData.isActive();
    }

    public void setActive(boolean active) {
        buttonData.setActive(active);
    }

    public void setPermissions(List<String> permissions) {
        buttonData.getPermissions().addAll(permissions);
    }

    public void setPermissions(String... permissions) {
        setPermissions(Arrays.asList(permissions));
    }

    public List<String> getPermissions() {
        return buttonData.getPermissions();
    }

    public void setTitle(String title) {
        this.buttonData.setTitle(title);
        setDisplayedName(title);
    }

    public void setMaterial(Material material) {
        itemStack.setType(material);
    }

    public void setPlayerHead(UUID uuid) {
        itemStack.setType(Material.PLAYER_HEAD);
        var skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        itemStack.setItemMeta(skullMeta);
    }

    public void setCustomMaterial(Material material, int id) {
        itemStack.setType(material);
        final var meta = itemStack.getItemMeta();
        if (meta == null) {
            return;
        }
        meta.setCustomModelData(id);
        itemStack.setItemMeta(meta);
    }

    public void setMaterialColor(Color color) {
        var meta = itemStack.getItemMeta();
        if (meta == null) {
            return;
        }
        if (meta instanceof LeatherArmorMeta horseMeta) {
            horseMeta.setColor(color);
            itemStack.setItemMeta(horseMeta);
        }
    }

    public void setItemMeta(ItemMeta meta) {
        itemStack.setItemMeta(meta);
    }

    public void editStyleRendererOptions(Consumer<StyleRendererOptionsDecorator> consumer) {
        var decorator = new StyleRendererOptionsDecorator(getStyleRendererOptions());
        consumer.accept(decorator);
    }

    public void setDescription(MessageBuilder messageBuilder) {
        setDescription(messageBuilder.toString());
    }

    public void setDescription(String... description) {
        setDescription(new ArrayList<>(Arrays.asList(description)));
    }

    public void setDescription(Consumer<MessageBuilder> consumer) {
        var builder = new MessageBuilder();
        consumer.accept(builder);
        setDescription(builder.toArray());
    }


    public List<String> getDescription() {
        return buttonData.getDescription();
    }

    public void addDescription(String... description) {
        buttonData.getDescription().addAll(Arrays.asList(description));
        setDescription(buttonData.getDescription());
    }


    public void updateDescription(int index, String value) {
        if (buttonData.getDescription() == null) {
            return;
        }
        buttonData.getDescription().set(index, value);
        final var meta = ensureMeta(itemStack);
        meta.setLore(buttonData.getDescription());
        itemStack.setItemMeta(meta);
    }

    public void setDescription(List<String> description) {
        buttonData.setDescription(description);
        var meta = ensureMeta(itemStack);
        if (meta == null) {
            return;
        }

        var finalArray = new ArrayList<String>(description);
        for (var i = 0; i < finalArray.size(); i++) {
            var line = finalArray.get(i);
            finalArray.set(i, ChatColor.WHITE + line);
        }

        meta.setLore(finalArray);
        itemStack.setItemMeta(meta);
    }


    public String getTitle() {
        return buttonData.getTitle();
    }

    /*Position*/

    public int getHeight() {
        return (int) buttonData.getPosition().getY();
    }

    public int getWidth() {
        return buttonData.getPosition().getBlockX();
    }

    public void setPosition(int height, int width) {
        buttonData.getPosition().setY(height);
        buttonData.getPosition().setX(width);
    }


    /*Sound*/
    public boolean hasSound() {
        return buttonData.getSound() != null;
    }

    public void setSound(Sound sound) {
        buttonData.setSound(sound);
    }

    public Sound getSound() {
        return buttonData.getSound();
    }


    private void setDisplayedName(String name) {
        var meta = ensureMeta(itemStack);
        if (meta == null)
            return;
        meta.setDisplayName(ChatColor.WHITE+name);
        itemStack.setItemMeta(meta);
    }

    public void setHighlighted(boolean value) {
        ItemMeta meta = ensureMeta(itemStack);
        if (meta == null)
            return;
        if (value)
            meta.addEnchant(Enchantment.ARROW_FIRE, 10, true);
        else
            meta.removeEnchant(Enchantment.ARROW_FIRE);
        buttonData.setHighLighted(value);
        itemStack.setItemMeta(meta);
    }

    public String getTag() {
        return buttonData.getTag();
    }

    public void setTag(String tag) {
        buttonData.setTag(tag);
    }

    public boolean hasTag() {
        return buttonData.getTag() != null;
    }

    public boolean hasTag(String tag) {
        return hasTag() && buttonData.getTag().equalsIgnoreCase(tag);
    }

    public boolean hasStyleRendererOptions() {
        return styleRendererOptions != null;
    }

    private void hideAttributes() {
        ItemMeta meta = ensureMeta(itemStack);
        if (meta == null)
            return;
        Arrays.asList(ItemFlag.values()).forEach(i -> meta.addItemFlags(i));
        itemStack.setItemMeta(meta);
    }

    private ItemMeta ensureMeta(ItemStack itemStack) {
        return itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }


    public void fromItemStack(ItemStack itemStack)
    {
        setMaterial(itemStack.getType());
        setItemMeta(itemStack.getItemMeta());
    }

}
