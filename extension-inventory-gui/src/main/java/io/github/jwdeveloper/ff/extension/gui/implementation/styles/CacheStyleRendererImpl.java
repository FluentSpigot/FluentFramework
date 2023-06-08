package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleColorPallet;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;

import java.util.HashMap;
import java.util.List;

public class CacheStyleRendererImpl extends DefaultStyleRenderer {

    private HashMap<String, List<String>> cache;


    public CacheStyleRendererImpl(FluentMessages messages, StyleColorPallet pallette, FluentTranslator translator) {
        super(messages, pallette, translator);
    }





   /* public CacheStyleRendererImpl(FluentTranslator translator, StyleColorSet buttonColorSet, FluentMessages simpleMessage) {
        super(translator, buttonColorSet, simpleMessage);
        cache = new HashMap<>();
    }

    @Override
    public List<String> render(StyleRendererOptions info)
    {

        if (cache.containsKey(info.getCacheID())) {
            return cache.get(info.getCacheID());
        }
        var result = super.render(info);
        if (info.hasCacheID()) {
            cache.put(info.getCacheID(), result);
        }
        return result;
    }*/
}
