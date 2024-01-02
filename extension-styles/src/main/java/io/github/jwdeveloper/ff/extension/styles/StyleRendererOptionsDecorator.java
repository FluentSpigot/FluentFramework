package io.github.jwdeveloper.ff.extension.styles;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.styles.styles.StyleRenderEvent;
import io.github.jwdeveloper.ff.extension.styles.styles.StyleRendererOptions;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class StyleRendererOptionsDecorator {
    private final StyleRendererOptions options;

    public StyleRendererOptionsDecorator(StyleRendererOptions options) {
        this.options = options;
    }

    public StyleRendererOptionsDecorator withUseCache() {
        options.setUseCache(true);
        if (!options.hasCacheID()) {
            options.setCacheId(UUID.randomUUID().toString());
        }
        return this;
    }


    public StyleRendererOptionsDecorator reset()
    {
        options.reset();
        return this;
    }

    public StyleRendererOptionsDecorator withCacheId(String cacheId) {
        options.setCacheId(cacheId);
        return this;
    }

    public StyleRendererOptionsDecorator withParameter(String parameter, String value) {
        options.addParameter(parameter, value);
        return this;
    }

    public StyleRendererOptionsDecorator withParameter(String parameter, Function<StyleRenderEvent, String> value) {
        options.addParameter(parameter, value);
        return this;
    }

    public StyleRendererOptionsDecorator withLeftClickInfo(Function<StyleRenderEvent, String> value) {
        options.addParameter("click-left", value);
        return this;
    }

    public StyleRendererOptionsDecorator withLeftClickInfo(String value) {
        withLeftClickInfo(x -> value);
        return this;
    }

    public StyleRendererOptionsDecorator withRightClickInfo(Function<StyleRenderEvent, String> value) {
        options.addParameter("click-right", value);
        return this;
    }

    public StyleRendererOptionsDecorator withRightClickInfo(String value) {
        withRightClickInfo(x -> value);
        return this;
    }

    public StyleRendererOptionsDecorator withShiftClickInfo(Function<StyleRenderEvent, String> value) {
        options.addParameter("click-shift", value);
        return this;
    }

    public StyleRendererOptionsDecorator withShiftClickInfo(String value) {
        withShiftClickInfo(x -> value);
        return this;
    }

    public StyleRendererOptionsDecorator withTitle(Function<StyleRenderEvent, String> value) {
        options.addParameter("title", value);
        return this;
    }

    public StyleRendererOptionsDecorator withTitle(String value) {
        withTitle(x -> value);
        return this;
    }

    public StyleRendererOptionsDecorator withDescription(Function<StyleRenderEvent, String> value) {
        options.addParameter("description", value);
        return this;
    }

    public StyleRendererOptionsDecorator withDescriptionLine(Function<StyleRenderEvent, String> value) {
        withDescriptionLine(UUID.randomUUID(), value);
        return this;
    }

    public StyleRendererOptionsDecorator withDescriptionLine(String id, Function<StyleRenderEvent, String> value) {
        options.addParameter("description-" + id, value);
        return this;
    }

    public StyleRendererOptionsDecorator withDescriptionLine(UUID id, Function<StyleRenderEvent, String> value) {
        options.addParameter("description-" + id, value);
        return this;
    }

    public StyleRendererOptionsDecorator withDescription(String value) {
        withDescription(x -> value);
        return this;
    }

    public StyleRendererOptionsDecorator withDescription(Consumer<MessageBuilder> value)
    {
        var builder = new MessageBuilder();
        value.accept(builder);
        var msg = builder.toString();
        withDescription(x -> msg);
        return this;
    }
}
