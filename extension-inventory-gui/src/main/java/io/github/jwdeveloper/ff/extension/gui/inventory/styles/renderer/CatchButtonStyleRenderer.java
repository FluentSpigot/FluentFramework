package io.github.jwdeveloper.ff.extension.gui.inventory.styles.renderer;

import io.github.jwdeveloper.ff.extension.gui.inventory.styles.ButtonColorSet;
import io.github.jwdeveloper.ff.extension.gui.inventory.styles.ButtonStyleInfo;
import io.github.jwdeveloper.ff.core.spigot.messages.SimpleMessage;
import io.github.jwdeveloper.ff.plugin.api.features.FluentTranslator;

import java.util.HashMap;
import java.util.List;

public class CatchButtonStyleRenderer extends ButtonStyleRenderer {

    private HashMap<String, List<String>> cache;

    public CatchButtonStyleRenderer(FluentTranslator translator, ButtonColorSet buttonColorSet, SimpleMessage simpleMessage) {
        super(translator, buttonColorSet, simpleMessage);
        cache = new HashMap<>();
    }

    @Override
    public List<String> render(ButtonStyleInfo info) {

        if (cache.containsKey(info.getId())) {
            return cache.get(info.getId());
        }
        var result = super.render(info);
        if (info.hasId()) {
            cache.put(info.getId(), result);
        }
        return result;
    }
}
