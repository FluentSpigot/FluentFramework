package tests;

import io.github.jwdeveloper.ff.extension.websocket.implementation.packet.JsonPacketResolver;
import io.github.jwdeveloper.ff.extension.websocket.implementation.packet.WebSocketPacket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class JsonPacketResolverTest
{
    @Test
    public void test()
    {
        var json = """
                {
                  "packetId":1,
                  "field1":"siema",
                  "field2":123,
                  "field3": "false"
                }
                """;


        var examplePacket =  new ExamplePacket(null);
        var map = new HashMap<Integer, WebSocketPacket>();
        map.put(examplePacket.getPacketId(), examplePacket);
        var resolver = new JsonPacketResolver();
        var result = resolver.resolveJsonPacket(json, map, null);


        Assertions.assertTrue(result);
        Assertions.assertEquals(examplePacket.field1,"siema");
        Assertions.assertEquals(examplePacket.field2,123);
        Assertions.assertEquals(examplePacket.field3,false);

    }
}
