package io.github.jwdeveloper.ff.extension.gui.api.styles;


import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;


public record StyleRenderEvent(FluentTranslator translator, StyleColorPallet pallet, MessageBuilder builder) {
}
