package io.github.jwdeveloper.ff.extension.websocket.implementation;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.extension.websocket.api.FluentWebsocket;
import io.github.jwdeveloper.ff.extension.websocket.api.data.WebsocketOptions;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import org.java_websocket.WebSocket;

public class FluentWebsocketImpl extends WebSocketBase implements FluentWebsocket
{
    private String serverIp;

    private WebsocketOptions options;

    public void setServerIp(String serverIp)
    {
        this.serverIp = serverIp;
    }

    public String getServerIp()
    {
     return serverIp;
    }

    @Override
    public int getPort() {
        return super.getPort();
    }

    public FluentWebsocketImpl(int port, PluginLogger logger, WebsocketOptions options) {
        super(port, logger);
        this.options = options;
    }

    @Override
    public void onError(WebSocket webSocket, Exception e)
    {
        options.getOnException().invoke(e);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        options.getOnClose().invoke(this);
    }

    @Override
    public void onStart() {
        options.getOnOpen().invoke(this);
    }
}
