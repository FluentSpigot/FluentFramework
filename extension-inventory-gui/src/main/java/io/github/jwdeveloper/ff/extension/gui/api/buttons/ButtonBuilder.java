package io.github.jwdeveloper.ff.extension.gui.api.buttons;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.OLD.observer_button.observers.ButtonObservable;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonBuilderImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.styles.StyleRendererOptionsDecorator;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface ButtonBuilder {
    ButtonBuilder withButtonObserver(ButtonObservable<?> observer);

    ButtonBuilder withOnLeftClick(Consumer<ButtonClickEvent> event);

    ButtonBuilder withOnRightClick(Consumer<ButtonClickEvent> event);

    ButtonBuilder withOnShiftClick(Consumer<ButtonClickEvent> event);

    ButtonBuilder withDataContext(Object content);

    ButtonBuilder withActive(boolean active);

    ButtonBuilder withPermissions(List<String> permissions);

    ButtonBuilder withPermissions(String... permissions);

    ButtonBuilder withTitle(String title);

    ButtonBuilder withMaterial(Material material);

    ButtonBuilder withMaterial(Material material, int customModelId);

    ButtonBuilder withMaterial(Material material, Color color);

    ButtonBuilder withMaterial(Material material, Color color, int customModelId);

    ButtonBuilder withPosition(int height, int width);

    ButtonBuilder withItemMeta(ItemMeta meta);

    ButtonBuilder withMaterialPlayerHead(UUID uuid);

    ButtonBuilder withSetDescription(MessageBuilder messageBuilder);

    ButtonBuilder withSetDescription(String... description);

    ButtonBuilder withSetDescription(Consumer<MessageBuilder> consumer);

    ButtonBuilder withSetDescription(List<String> description);

    ButtonBuilder withAddDescription(String... description);

    ButtonBuilder withSound(Sound sound);

    ButtonBuilder withReference(ButtonRef reference);

    ButtonBuilder withStyleRenderer(Consumer<StyleRendererOptionsDecorator> consumer);

    ButtonBuilder withHighlighted(boolean value);

    ButtonBuilder withHighlighted();

    ButtonBuilder withTag(String tag);
}
