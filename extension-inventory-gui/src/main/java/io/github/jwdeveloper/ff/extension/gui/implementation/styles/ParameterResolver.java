package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.core.common.ColorPallet;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderEvent;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRendererOptions;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

import java.util.ArrayList;
import java.util.List;


public class ParameterResolver {
    private final StyleRendererOptions rendererOptions;
    private final FluentTranslator translator;
    private final ColorPallet colorPallet;
    private final DefaultTextRenderer defaultTextRenderer;

    public ParameterResolver(StyleRendererOptions rendererOptions, FluentTranslator translator, ColorPallet colorPallet) {
        this.rendererOptions = rendererOptions;
        this.translator = translator;
        this.colorPallet = colorPallet;
        this.defaultTextRenderer = new DefaultTextRenderer(FluentApi.plugin(), colorPallet);
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
            return provider.apply(new StyleRenderEvent(translator, colorPallet, new MessageBuilder(),defaultTextRenderer));
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Error while rendering parameter "+parameter,e);
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
