package io.github.jwdeveloper.ff.extension.protocol.api;

import io.github.jwdeveloper.ff.extension.protocol.impl.FluentProtocolNms;
import io.github.jwdeveloper.ff.extension.protocol.impl.builder.PacketProxyBuilder;
import io.github.jwdeveloper.reflect.implementation.FluentReflect;
import org.bukkit.entity.Player;

public interface FluentProtocol
{
    FluentProtocolNms NMS();
    FluentReflect reflections();
    void onPacket(PacketProxy packetProxy);
    PacketProxyBuilder onPacket();
    PacketProxyBuilder onPacket(Player player);
}
