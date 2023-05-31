package io.github.jwdeveloper.ff.extension.websocket;

import io.github.jwdeveloper.ff.extension.websocket.api.data.WebsocketOptions;
import io.github.jwdeveloper.ff.extension.websocket.implementation.WebsocketExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentWebsocketApi
{
    public static FluentApiExtension use(Consumer<WebsocketOptions> options)
    {
        return new WebsocketExtension(options);
    }

    public static FluentApiExtension use()
    {
        return new WebsocketExtension((x)->{});
    }
}
