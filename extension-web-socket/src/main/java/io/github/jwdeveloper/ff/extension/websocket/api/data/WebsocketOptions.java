package io.github.jwdeveloper.ff.extension.websocket.api.data;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.websocket.api.FluentWebsocket;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionOptions;
import lombok.Data;
import lombok.Getter;

@Data
public class WebsocketOptions
{
    private int defaultPort = 443;

    private String configPath = "plugin.websocket";

    @Getter
    private EventGroup<FluentWebsocket> onOpen = new EventGroup<>();

    @Getter
    private EventGroup<FluentWebsocket> onClose = new EventGroup<>();

    @Getter
    private EventGroup<Exception> onException = new EventGroup<>();
}
