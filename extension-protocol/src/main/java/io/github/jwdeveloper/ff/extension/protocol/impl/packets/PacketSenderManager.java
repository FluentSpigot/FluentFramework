package io.github.jwdeveloper.ff.extension.protocol.impl.packets;


import io.github.jwdeveloper.ff.extension.protocol.impl.FluentProtocolNms;
import io.github.jwdeveloper.reflect.implementation.models.JavaFieldModel;
import io.github.jwdeveloper.reflect.implementation.models.JavaMethodModel;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.util.Tuple;
import org.bukkit.entity.Player;

import java.util.WeakHashMap;

public class PacketSenderManager {

    private final JavaMethodModel M_NETWORK_MANAGER__SEND;

    private final WeakHashMap<Player, Tuple<Object, Channel>> networkManagerAndChannelCache;

    private final JavaFieldModel
            F_CRAFT_PLAYER__HANDLE,
            F_ENTITY_PLAYER__CONNECTION,
            F_PLAYER_CONNECTION__NETWORK_MANAGER,
            F_NETWORK_MANAGER__CHANNEL;

    public PacketSenderManager(FluentProtocolNms nms) {
        networkManagerAndChannelCache = new WeakHashMap<>();
        var C_CRAFT_PLAYER = nms.CRAFT_PLAYER_V.find();
        var C_ENTITY_PLAYER = nms.ENTITY_PLAYER.find();
        var C_PLAYER_CONNECTION = nms.PLAYER_CONNECTION.find();
        var C_NETWORK_MANAGER = nms.NETWORK_MANAGER.find();
        var C_PACKET = nms.PACKET.find();

        F_CRAFT_PLAYER__HANDLE = C_CRAFT_PLAYER
                .findField()
                .forAnyVersion(finder ->
                {
                    finder.withProtected();
                    finder.withType(C_ENTITY_PLAYER.getClassType());
                }).find();

        F_ENTITY_PLAYER__CONNECTION = C_ENTITY_PLAYER
                .findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(C_PLAYER_CONNECTION.getClassType());
                }).find();

        F_PLAYER_CONNECTION__NETWORK_MANAGER = C_PLAYER_CONNECTION
                .findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(C_NETWORK_MANAGER.getClassType());
                }).find();

        F_NETWORK_MANAGER__CHANNEL = C_NETWORK_MANAGER
                .findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(Channel.class);
                }).find();

        M_NETWORK_MANAGER__SEND = C_NETWORK_MANAGER
                .findMethod()
                .forAnyVersion(finder ->
                {
                    finder.withParameter(C_PACKET.getClassType());
                    finder.withParameter(GenericFutureListener.class);
                }).find();
    }

    private Tuple<Object, Channel> findNetworkManagerAndChannel(Player player) throws Exception {
        var entityPlayer = F_CRAFT_PLAYER__HANDLE.getValue(player);
        var playerConnection = F_ENTITY_PLAYER__CONNECTION.getValue(entityPlayer);
        var networkManager = F_PLAYER_CONNECTION__NETWORK_MANAGER.getValue(playerConnection);
        var channel = F_NETWORK_MANAGER__CHANNEL.getValue(networkManager);
        return new Tuple<>(networkManager, (Channel) channel);
    }

    public void sendPacket(Player player, Object packet, Runnable completion) throws Exception {
        var networkManagerAndChannel = networkManagerAndChannelCache.get(player);

        if (networkManagerAndChannel == null) {
            networkManagerAndChannel = findNetworkManagerAndChannel(player);
            networkManagerAndChannelCache.put(player, networkManagerAndChannel);
        }

        if (!networkManagerAndChannel.b().isOpen())
            return;

        sendPacket(networkManagerAndChannel.a(), packet, completion);
    }

    public void sendPacket(Object networkManager, Object packet, Runnable completion) throws Exception {
        GenericFutureListener<? extends Future<? super Void>> listener = null;

        if (completion != null)
            listener = v -> completion.run();

        M_NETWORK_MANAGER__SEND.invoke(networkManager, packet, listener);
    }

}