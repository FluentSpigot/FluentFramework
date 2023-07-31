package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.styles.ColorPallet;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderEvent;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRendererOptions;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;

import java.util.ArrayList;
import java.util.List;


public class ParameterResolver {
    private final StyleRendererOptions rendererOptions;
    private final FluentTranslator translator;
    private final ColorPallet colorPallet;

    public ParameterResolver(StyleRendererOptions rendererOptions, FluentTranslator translator, ColorPallet colorPallet) {
        this.rendererOptions = rendererOptions;
        this.translator = translator;
        this.colorPallet = colorPallet;
    }

    public boolean has(String parameter) {
        return rendererOptions.hasParameter(parameter);
    }

    public boolean hasGroup(String groupName) {
        return !rendererOptions.getParametersByContains(groupName).isEmpty();
    }

    public String get(String parameter) {
        var optional = rendererOptions.getParameter(parameter);
        if (optional.isEmpty()) {
            return StringUtils.EMPTY;
        }
        try {
            var provider = optional.get();
            return provider.apply(new StyleRenderEvent(translator, colorPallet, new MessageBuilder(),null));
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    public List<String> getGroup(String parameter) {
        var query = rendererOptions.getParametersByContains(parameter);
        var result = new ArrayList<String>();
        for (var entry : query.entrySet()) {
            var value = get(entry.getKey());
            result.add(value);
        }
        return result;
    }
}
