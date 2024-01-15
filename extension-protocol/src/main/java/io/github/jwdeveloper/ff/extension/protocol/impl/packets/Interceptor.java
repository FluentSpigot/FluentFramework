package io.github.jwdeveloper.ff.extension.protocol.impl.packets;


import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.protocol.api.PacketProxy;
import io.github.jwdeveloper.ff.extension.protocol.impl.packets.PacketOperator;
import io.netty.channel.*;
import lombok.Setter;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Consumer;

public class Interceptor extends ChannelDuplexHandler {
    // Don't keep closed channels from being garbage-collected
    private final WeakReference<Channel> channel;
    private final PacketOperator operator;
    private String handlerName;
    private Object networkManager;
    private volatile String playerName;

    @Setter
    private PacketProxy packetProxy;

    // * Create a new packet interceptor on top of a network channel
    //  * @param channel Underlying network channel to intercept data on
    //  * @param playerName Name of the player, if it's already known at the time of instantiation
    //  * @param operator External packet operator which does all reflective access

    public Interceptor(Channel channel, String playerName, PacketOperator operator) {
        this.playerName = playerName;
        this.channel = new WeakReference<>(channel);
        this.operator = operator;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Channel ch = channel.get();

        // Try to extract the name and update the local reference, if applicable
        try {
            String extractedName = operator.tryExtractName(this, o);
            if (extractedName != null)
                playerName = extractedName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Call the inbound interceptor, if applicable
        if (packetProxy != null && ch != null) {
            try {
                o = packetProxy.onIncomingData(playerName, o, ch);
            } catch (Exception e) {
                FluentLogger.LOGGER.error("onIncomingData packet error",e);
            }

            // Dropped the packet
            if (o == null)
                return;
        }

        super.channelRead(channelHandlerContext, o);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object o, ChannelPromise channelPromise) throws Exception {
        Channel ch = channel.get();

        // Call the outbound interceptor, if applicable
        if (packetProxy != null && ch != null) {
            try {
                o = packetProxy.onOutcomingData(playerName, o, ch);
            } catch (Exception e)
            {
                FluentLogger.LOGGER.error("onIncomingData packet error",e);
            }

            // Dropped the packet
            if (o == null)
                return;
        }

        super.write(channelHandlerContext, o, channelPromise);
    }


    // * Calls the consumer with the pipe of the underlying channel if
    //  * the channel is present and hasn't yet been garbage-collected
    // * @param action Consumer of the pipe

    private void ifPipePresent(Consumer<ChannelPipeline> action) {
        Channel ch = channel.get();

        if (ch == null)
            return;

        action.accept(ch.pipeline());
    }


//   * Attaches this interceptor to it's underlying channel
    //  * @param name Name to attach as within the pipeline

    public void attach(String name) {
        if (this.handlerName != null)
            throw new IllegalStateException("Tried to attach twice");

        ifPipePresent(pipe -> {
            // The network manager instance is also registered within the
            // pipe, get it by it's name to have a reference available
            networkManager = pipe.get("packet_handler");

            // Register before the packet handler to have an interception capability
            pipe.addBefore("packet_handler", name, this);
            this.handlerName = name;
        });
    }


    //  Detaches this interceptor from it's underlying channel

    public void detach() {
        if (this.handlerName == null)
            return;

        ifPipePresent(pipe -> {
            List<String> names = pipe.names();

            if (!names.contains(handlerName))
                return;

            pipe.remove(handlerName);
        });

        this.networkManager = null;
        this.handlerName = null;
    }


    public void sendPacket(Object packet, Runnable completion) throws Exception {
        if (networkManager == null)
            throw new IllegalStateException("Could not find the network manager");

        this.operator.sendPacket(packet, completion, networkManager);
    }
}