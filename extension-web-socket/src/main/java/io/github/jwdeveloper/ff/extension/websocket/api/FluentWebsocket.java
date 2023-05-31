package io.github.jwdeveloper.ff.extension.websocket.api;

import java.util.Collection;

public interface FluentWebsocket
{
    String getServerIp();

    int getPort();

    void start();

    void stop() throws InterruptedException;

     void registerPackets(Collection<FluentWebsocketPacket> packets);
}
