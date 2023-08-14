package io.github.jwdeveloper.ff.extension.gui.implementation.buttons;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.OLD.observer_button.observers.ButtonObservable;
import io.github.jwdeveloper.ff.extension.gui.implementation.styles.StyleRendererOptionsDecorator;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ButtonBuilderImpl implements ButtonBuilder {
    private final ButtonUI buttonUI;

    public ButtonBuilderImpl() {
        buttonUI = new ButtonUI();
    }

    protected ButtonBuilderImpl self() {
        return this;
    }


    @Override
    public ButtonBuilder withButtonObserver(ButtonObservable<?> observer) {
        return this;
    }

    @Override
    public ButtonBuilderImpl withOnLeftClick(Consumer<ButtonClickEvent> event) {
        buttonUI.getOnLeftClick().subscribe(event);
        return self();
    }

    @Override
    public ButtonBuilderImpl withOnRightClick(Consumer<ButtonClickEvent> event) {
        buttonUI.getOnRightClick().subscribe(event);
        return self();
    }

    @Override
    public ButtonBuilderImpl withOnShiftClick(Consumer<ButtonClickEvent> event) {
        buttonUI.getOnShiftClick().subscribe(event);
        return self();
    }

    @Override
    public ButtonBuilderImpl withDataContext(Object content) {
        buttonUI.setDataContext(content);
        return self();
    }

    @Override
    public ButtonBuilderImpl withActive(boolean active) {
        buttonUI.setActive(active);
        return self();
    }

    @Override
    public ButtonBuilderImpl withPermissions(List<String> permissions) {
        buttonUI.setPermissions(permissions);
        return self();
    }

    @Override
    public ButtonBuilderImpl withPermissions(String... permissions) {
        buttonUI.setPermissions(permissions);
        return self();
    }


    @Override
    public ButtonBuilderImpl withTitle(String title) {
        buttonUI.setTitle(title);
        return self();
    }

    @Override
    public ButtonBuilderImpl withMaterial(Material material) {
        buttonUI.setMaterial(material);
        return self();
    }

    @Override
    public ButtonBuilderImpl withMaterial(Material material, int customModelId) {
        buttonUI.setCustomMaterial(material, customModelId);
        return self();
    }

    @Override
    public ButtonBuilderImpl withMaterial(Material material, Color color) {
        buttonUI.setMaterial(material);
        buttonUI.setMaterialColor(color);
        return self();
    }

    @Override
    public ButtonBuilderImpl withMaterial(Material material, Color color, int customModelId) {
        buttonUI.setCustomMaterial(material, customModelId);
        buttonUI.setMaterialColor(color);
        return self();
    }

    @Override
    public ButtonBuilderImpl withPosition(int height, int width) {
        buttonUI.setPosition(height, width);
        return self();
    }

    @Override
    public ButtonBuilderImpl withItemMeta(ItemMeta meta) {
        buttonUI.setItemMeta(meta);
        return self();
    }

    @Override
    public ButtonBuilderImpl withMaterialPlayerHead(UUID uuid) {
        buttonUI.setPlayerHead(uuid);
        return self();
    }

    @Override
    public ButtonBuilderImpl withSetDescription(MessageBuilder messageBuilder) {
        buttonUI.setDescription(messageBuilder);
        return self();
    }

    @Override
    public ButtonBuilderImpl withSetDescription(String... description) {
        return withSetDescription(new ArrayList<>(Arrays.asList(description)));
    }

    @Override
    public ButtonBuilderImpl withSetDescription(Consumer<MessageBuilder> consumer) {
        buttonUI.setDescription(consumer);
        return self();
    }

    @Override
    public ButtonBuilderImpl withSetDescription(List<String> description) {
        buttonUI.setDescription(description);
        return self();
    }

    @Override
    public ButtonBuilderImpl withAddDescription(String... description) {
        buttonUI.addDescription(description);
        return self();
    }

    @Override
    public ButtonBuilderImpl withSound(Sound sound) {
        buttonUI.setSound(sound);
        return self();
    }

    @Override
    public ButtonBuilderImpl withReference(ButtonRef reference) {
        reference.set(buttonUI);
        return self();
    }

    @Override
    public ButtonBuilderImpl withStyleRenderer(Consumer<StyleRendererOptionsDecorator> consumer) {
        var decorator = new StyleRendererOptionsDecorator(buttonUI.getStyleRendererOptions());
        consumer.accept(decorator);
        return self();
    }

    @Override
    public ButtonBuilderImpl withHighlighted(boolean value) {
        buttonUI.setHighlighted(value);
        return self();
    }
    @Override
    public ButtonBuilderImpl withHighlighted() {
        buttonUI.setHighlighted(true);
        return self();
    }

    @Override
    public ButtonBuilderImpl withTag(String tag) {
        buttonUI.setTag(tag);
        return self();
    }

    public ButtonUI build() {
        return buttonUI;
    }
}
