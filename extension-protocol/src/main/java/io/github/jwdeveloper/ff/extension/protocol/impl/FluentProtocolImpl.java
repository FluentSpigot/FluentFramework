package io.github.jwdeveloper.ff.extension.protocol.impl;

import io.github.jwdeveloper.ff.extension.protocol.api.FluentProtocol;
import io.github.jwdeveloper.ff.extension.protocol.api.PacketProxy;
import io.github.jwdeveloper.ff.extension.protocol.impl.builder.PacketProxyBuilder;
import io.github.jwdeveloper.reflect.implementation.FluentReflect;
import lombok.Getter;
import org.bukkit.entity.Player;


public class FluentProtocolImpl implements FluentProtocol
{
    private final FluentProtocolNms nms;
    private final FluentReflect reflect;

    public FluentProtocolImpl(FluentProtocolNms nms, FluentReflect reflect) {
        this.nms = nms;
        this.reflect = reflect;
    }

    @Override
    public FluentProtocolNms NMS() {
        return nms;
    }

    @Override
    public FluentReflect reflections()
    {
        return reflect;
    }

    @Override
    public void onPacket(PacketProxy packetProxy) {

    }

    @Override
    public PacketProxyBuilder onPacket() {
        return null;
    }

    @Override
    public PacketProxyBuilder onPacket(Player player) {
        return null;
    }


}
