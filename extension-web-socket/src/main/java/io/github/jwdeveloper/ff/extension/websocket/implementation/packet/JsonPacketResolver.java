package io.github.jwdeveloper.ff.extension.websocket.implementation.packet;

import com.google.gson.JsonParser;
import lombok.Data;
import org.java_websocket.WebSocket;

import java.util.Map;

@Data
public class JsonPacketResolver {


    public boolean resolveJsonPacket(String jsonContent, Map<Integer, WebSocketPacket> webSocketEvents, WebSocket webSocket) {
        var json = JsonParser.parseString(jsonContent);
        var jsonObject = json.getAsJsonObject();
        if (!jsonObject.has("packetId")) {
            throw new RuntimeException("packet id is required: " + jsonContent);
        }

        var packetId = jsonObject.get("packetId").getAsInt();

        if (!webSocketEvents.containsKey(packetId)) {
            return false;
        }

        var packet = webSocketEvents.get(packetId);

        var resolved = packet.resolveJson(jsonObject);
        if(!resolved)
        {
            return false;
        }
        packet.onPacketTriggered(webSocket);
        return true;
    }

}
