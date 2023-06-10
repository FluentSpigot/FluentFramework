package io.github.jwdeveloper.ff.extension.gui.implementation.styles;

import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderEvent;
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

    public void withParameter(String parameter, Function<StyleRenderEvent, String> value) {
        options.addParameter(parameter, value);
    }

    public void withLeftClickInfo(Function<StyleRenderEvent, String> value) {
        options.addParameter("click-left", value);
    }

    public void withLeftClickInfo(String value) {
        withLeftClickInfo(x -> value);
    }

    public void withRightClickInfo(Function<StyleRenderEvent, String> value) {
        options.addParameter("click-right", value);
    }

    public void withRightClickInfo(String value) {
        withRightClickInfo(x -> value);
    }

    public void withShiftClickInfo(Function<StyleRenderEvent, String> value) {
        options.addParameter("click-shift", value);
    }

    public void withShiftClickInfo(String value) {
        withShiftClickInfo(x -> value);
    }

    public void withTitle(Function<StyleRenderEvent, String> value) {
        options.addParameter("title", value);
    }

    public void withTitle(String value) {
        withTitle(x -> value);
    }

    public void withDescription(Function<StyleRenderEvent, String> value) {
        options.addParameter("description", value);
    }

    public void withDescriptionLine(String id, Function<StyleRenderEvent, String> value) {
        options.addParameter("description-"+id, value);
    }

    public void withDescriptionLine(UUID id, Function<StyleRenderEvent, String> value) {
        options.addParameter("description-"+id, value);
    }

    public void withDescription(String value) {
        withDescription(x -> value);
    }
}
