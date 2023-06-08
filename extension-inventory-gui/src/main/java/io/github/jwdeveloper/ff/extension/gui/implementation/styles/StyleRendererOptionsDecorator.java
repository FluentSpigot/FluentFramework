package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleEvent;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRendererOptions;

import java.util.UUID;
import java.util.function.Function;

public class StyleRendererOptionsDecorator {
    private final StyleRendererOptions options;

    public StyleRendererOptionsDecorator(StyleRendererOptions options) {
        this.options = options;
    }

    public void withUseCache()
    {
        options.setUseCache(true);
        if(!options.hasCacheID())
        {
            options.setCacheId(UUID.randomUUID().toString());
        }
    }
    public void withCacheId(String cacheId) {
        options.setCacheId(cacheId);
    }

    public void withParameter(String parameter, String value) {
        options.addParameter(parameter, value);
    }

    public void withParameter(String parameter, Function<StyleEvent, String> value) {
        options.addParameter(parameter, value);
    }

    public void withLeftClickInfo(Function<StyleEvent, String> value) {
        options.addParameter("click-left", value);
    }

    public void withLeftClickInfo(String value) {
        withLeftClickInfo(x -> value);
    }

    public void withRightClickInfo(Function<StyleEvent, String> value) {
        options.addParameter("click-right", value);
    }

    public void withRightClickInfo(String value) {
        withRightClickInfo(x -> value);
    }

    public void withShiftClickInfo(Function<StyleEvent, String> value) {
        options.addParameter("click-shift", value);
    }

    public void withShiftClickInfo(String value) {
        withShiftClickInfo(x -> value);
    }

    public void withTitle(Function<StyleEvent, String> value) {
        options.addParameter("title", value);
    }

    public void withTitle(String value) {
        withTitle(x -> value);
    }

    public void withDescription(Function<StyleEvent, String> value) {
        options.addParameter("description", value);
    }

    public void withDescription(String value) {
        withDescription(x -> value);
    }
}
