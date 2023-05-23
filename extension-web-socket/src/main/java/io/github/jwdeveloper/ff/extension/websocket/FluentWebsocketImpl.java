package io.github.jwdeveloper.ff.extension.websocket;

import io.github.jwdeveloper.ff.extension.websocket.api.FluentWebsocket;
import io.github.jwdeveloper.ff.extension.websocket.core.api.FluentWebsocketPacket;
import io.github.jwdeveloper.ff.extension.websocket.core.implementation.WebSocketBase;
import io.github.jwdeveloper.ff.core.common.logger.BukkitLogger;

import java.util.Collection;

public class FluentWebsocketImpl extends WebSocketBase implements FluentWebsocket
{
    private String serverIp;

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

    public FluentWebsocketImpl(int port, BukkitLogger logger) {
        super(port, logger);
    }

    @Override
    public void stop() throws InterruptedException {
        super.stop();
    }

    @Override
    public void registerPackets(Collection<FluentWebsocketPacket> packets) {
        super.registerPackets(packets);
    }

    @Override
    public void start() {
        super.start();
    }
}
