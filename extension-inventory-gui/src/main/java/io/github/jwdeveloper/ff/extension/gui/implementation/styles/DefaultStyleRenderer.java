package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleColorPallet;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderer;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRendererOptions;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultStyleRenderer implements StyleRenderer {

    private final FluentMessages messages;
    private final StyleColorPallet pallet;
    private final FluentTranslator translator;

    private final Map<String, List<String>> cachedRenders;

    public DefaultStyleRenderer(FluentMessages messages, StyleColorPallet pallet, FluentTranslator translator) {
        this.messages = messages;
        this.pallet = pallet;
        this.translator = translator;
        cachedRenders = new HashMap<>();
    }

    @Override
    public void render(ButtonUI buttonUI, StyleRendererOptions options) {
        if (!options.hasAnyParameter()) {
            return;
        }

        if (options.isUseCache() && cachedRenders.containsKey(options.getCacheId())) {
            buttonUI.setDescription(cachedRenders.get(options.getCacheId()));
            return;
        }

        var resolver = new ParameterResolver(options, translator, pallet);
        var builder = messages.chat();
        var description = createButtonLore(resolver, builder);
        buttonUI.setDescription(description);

        if (options.isUseCache()) {
            cachedRenders.put(options.getCacheId(), description);
        }
    }

    public List<String> createButtonLore(ParameterResolver resolver, MessageBuilder builder) {
        if (resolver.has("title")) {
            builder.text("title: ").space().text(resolver.get("title")).newLine();
        }

        builder.newLine();
        builder.bar("=", 30, pallet.getSecondary());
        builder.newLine();
        for (var description : resolver.getAllByContains("description")) {
            builder.text(description).newLine();
        }
        builder.newLine();
        builder.bar("=", 30, pallet.getSecondary());
        builder.newLine();
        if (resolver.has("click-left")) {
            builder.text("click-left: ").space().text(resolver.get("click-left")).newLine();
        }

        if (resolver.has("click-right")) {
            builder.text("click-right: ").space().text(resolver.get("click-right")).newLine();
        }

        if (resolver.has("click-shift")) {
            builder.text("click-shift: ").space().text(resolver.get("click-shift")).newLine();
        }

        builder.bar("=", 30, pallet.getSecondary());

        return builder.toList();
    }


}
