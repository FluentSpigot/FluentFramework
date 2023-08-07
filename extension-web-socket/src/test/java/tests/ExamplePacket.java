package tests;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.websocket.api.annotations.PacketProperty;
import io.github.jwdeveloper.ff.extension.websocket.implementation.packet.WebSocketPacket;
import lombok.Getter;
import org.java_websocket.WebSocket;

@Getter
public class ExamplePacket extends WebSocketPacket
{

    @PacketProperty
    public String field1;

    @PacketProperty
    public Integer field2;

    @PacketProperty
    public Boolean field3;
    public ExamplePacket(FluentTaskFactory manager) {
        super(manager);
    }

    @Override
    public void onPacketTriggered(WebSocket webSocket) {

    }

    @Override
    public int getPacketId() {
        return 1;
    }
}
