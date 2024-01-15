package io.github.jwdeveloper.ff.extension.protocol.api;

import io.netty.channel.Channel;

public interface PacketProxy
{
    /**
     * Called whenever a packet passes through the intercepted pipeline
     * @param senderName Name of the sender, only non-null at and after the LoginIn-Packet
     * @param packet Packet instance
     * @param channel Intercepted channel
     * @return The received (and possibly modified) packet or null to drop the packet
     */
    Object onIncomingData(String senderName, Object packet, Channel channel) throws Exception;

    Object onOutcomingData(String senderName, Object packet, Channel channel) throws Exception;
}
