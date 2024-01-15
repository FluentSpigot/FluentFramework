package io.github.jwdeveloper.ff.extension.protocol.impl.packets;


import com.mojang.authlib.GameProfile;
import io.github.jwdeveloper.ff.extension.protocol.impl.FluentProtocolNms;
import io.github.jwdeveloper.reflect.implementation.models.JavaClassModel;
import io.github.jwdeveloper.reflect.implementation.models.JavaFieldModel;
import io.github.jwdeveloper.reflect.implementation.models.JavaMethodModel;
import io.netty.channel.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

public class PacketOperator {
    private final String handlerName;
    private final JavaClassModel C_GAME_PROFILE;
    private final JavaClassModel C_PACKET_LOGIN;
    private final JavaMethodModel M_CRAFT_PLAYER__HANDLE;
    private final JavaFieldModel F_PACKET_LOGIN__GAME_PROFILE;
    private JavaFieldModel F_PACKET_LOGIN__NAME;

    private final JavaFieldModel
            F_CRAFT_SERVER__MINECRAFT_SERVER,
            F_MINECRAFT_SERVER__SERVER_CONNECTION,
            F_SERVER_CONNECTION__CHANNEL_FUTURES,
            F_ENTITY_PLAYER__PLAYER_CONNECTION,
            F_PLAYER_CONNECTION__NETWORK_MANAGER,
            F_NETWORK_MANAGER__CHANNEL;

    private final Map<Channel, ChannelInboundHandlerAdapter> channelHandlers;
    private final WeakHashMap<String, Interceptor> interceptorByPlayerName;
    private final List<Interceptor> interceptors;
    private PacketSenderManager senderManager;

    public PacketOperator(PacketSenderManager senderManager,
                          FluentProtocolNms nms,
                          String handlerName)
    {
        this.senderManager = senderManager;
        this.handlerName = handlerName;
        this.interceptors = new ArrayList<>();
        this.channelHandlers = new HashMap<>();
        this.interceptorByPlayerName = new WeakHashMap<>();

        JavaClassModel C_CRAFT_PLAYER = nms.CRAFT_PLAYER_V.find();
        JavaClassModel C_ENTITY_PLAYER = nms.ENTITY_PLAYER.find();
        JavaClassModel C_PLAYER_CONNECTION = nms.PLAYER_CONNECTION.find();
        JavaClassModel C_NETWORK_MANAGER = nms.NETWORK_MANAGER.find();
        JavaClassModel C_CRAFT_SERVER = nms.CRAFT_SERVER_V.find();
        JavaClassModel C_MINECRAFT_SERVER = nms.MINECRAFT_SERVER.find();
        JavaClassModel C_SERVER_CONNECTION = nms.SERVER_CONNECTION.find();
        C_GAME_PROFILE = nms.GAME_PROFILE.find();
        C_PACKET_LOGIN = nms.PACKET_I_LOGIN.find();

        F_CRAFT_SERVER__MINECRAFT_SERVER = C_CRAFT_SERVER
                .findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(C_MINECRAFT_SERVER.getClassType());
                }).find();


        F_MINECRAFT_SERVER__SERVER_CONNECTION = C_MINECRAFT_SERVER.findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(C_SERVER_CONNECTION.getClassType());
                }).find();


        F_SERVER_CONNECTION__CHANNEL_FUTURES = C_SERVER_CONNECTION.findField()
                .forAnyVersion(finder ->
                {
                    finder.withFinal();
                    finder.withPrivate();
                    finder.withType(List.class);
                    finder.withGenericType(ChannelFuture.class.getName());
                }).find();


        F_PACKET_LOGIN__GAME_PROFILE = C_PACKET_LOGIN.findField()
                .forAnyVersion(finder ->
                {
                    finder.withPrivate();
                    finder.withFinal();
                    finder.withType(C_GAME_PROFILE.getClassType());
                }).find();


        if (F_PACKET_LOGIN__GAME_PROFILE == null) {
            F_PACKET_LOGIN__NAME = C_PACKET_LOGIN.findField().forAnyVersion(finder ->
            {
                finder.withType(String.class);
            }).find();
        }

        M_CRAFT_PLAYER__HANDLE = C_CRAFT_PLAYER.findMethod().forAnyVersion(finder ->
        {
            finder.withType(C_ENTITY_PLAYER.getClassType());
        }).find();

        F_ENTITY_PLAYER__PLAYER_CONNECTION = C_ENTITY_PLAYER
                .findField()
                .forAnyVersion(e ->
                {
                    e.withType(C_PLAYER_CONNECTION.getClassType());
                }).find();

        F_PLAYER_CONNECTION__NETWORK_MANAGER = C_PLAYER_CONNECTION.findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(C_NETWORK_MANAGER.getClassType());
                }).find();

        F_NETWORK_MANAGER__CHANNEL = C_NETWORK_MANAGER.findField().forAnyVersion(finder ->
        {
            finder.withType(Channel.class);
        }).find();
    }


//   * Attaches an initialization listener function to a channel which will get called
    //  * as soon as that channel is fully initialized and ready to be injected
    //  * @param channel Channel to attach the listener to
    //  * @param container Container to synchronize over while waiting for initialization
    //  * @param initialized Callback containing the initialized channel
    // @return Listener handle to detach after use

    private ChannelInboundHandlerAdapter attachInitializationListener(Channel channel,
                                                                      List<?> container,
                                                                      Consumer<Channel> initialized) {
        // Called when a new channel has been instantiated
        var adapter = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) {
                ((Channel) o).pipeline().addFirst(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel channel) {

                        // Add this initializer as the first item in the channel to be the
                        // first receiver which gets a hold of it
                        channel.pipeline().addFirst(new ChannelInitializer<Channel>() {

                            @Override
                            protected void initChannel(Channel channel) {
                                // Wait for initialization
                                synchronized (container) {
                                    channel.eventLoop().submit(() -> initialized.accept(channel));
                                }
                            }
                        });
                    }
                });

                channelHandlerContext.fireChannelRead(o);
            }
        };

        channel.pipeline().addFirst(adapter);
        return adapter;
    }


    // * Attach an initialization listener using {@link #attachInitializationListener}
    // * to every currently available server channel
    //  * @param interceptor Consumer of internally created interception instances

    private void attachInitializationListeners(Consumer<Interceptor> interceptor) {
        try {
            Object minecraftServer = F_CRAFT_SERVER__MINECRAFT_SERVER.getValue(Bukkit.getServer());
            Object serverConnection = F_MINECRAFT_SERVER__SERVER_CONNECTION.getValue(minecraftServer);
            List<?> futures = (List<?>) F_SERVER_CONNECTION__CHANNEL_FUTURES.getValue(serverConnection);

            for (Object item : futures) {
                ChannelFuture future = (ChannelFuture) item;
                Channel channel = future.channel();

                ChannelInboundHandlerAdapter handler = attachInitializationListener(channel, futures, newChannel -> {
                    interceptor.accept(this.attachInterceptor(newChannel, null));
                });

                this.channelHandlers.put(channel, handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // * Detaches all initialization listeners which got previously attached
    //  * by using {@link #attachInitializationListeners}

    private void detachInitializationListeners() {
        Iterator<Map.Entry<Channel, ChannelInboundHandlerAdapter>> it = this.channelHandlers.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Channel, ChannelInboundHandlerAdapter> entry = it.next();
            it.remove();

            Channel channel = entry.getKey();

            if (!channel.isOpen())
                continue;

            channel.pipeline().remove(entry.getValue());
        }
    }


    //  * Calls {@link Interceptor#detach} on all interceptors previously added
    //  * by using {@link #attachInterceptor}

    private void detachInterceptors() {
        Iterator<Interceptor> it = this.interceptors.iterator();

        while (it.hasNext()) {
            it.next().detach();
            it.remove();
        }
    }


    // * Attaches an interceptor on a specific channel with the provided handler name
    //* @param channel Channel to attach an interceptor to
    //  * @param playerName Name of the player, if it's already known at the time of instantiation
    //      * @return The attached interceptor instance

    private Interceptor attachInterceptor(Channel channel, String playerName) {
        Interceptor interceptor = new Interceptor(channel, playerName, this);

        interceptor.attach(handlerName);
        interceptors.add(interceptor);

        // Detach and remove when this channel has been closed
        channel.closeFuture().addListener(future -> {
            interceptor.detach();
            interceptors.remove(interceptor);
        });

        return interceptor;
    }


    // * Get the channel of a player's network manager
    //      * @param p Target player to get the channel of
    //  * @return Channel on success, null on internal errors

    private io.netty.channel.Channel getPlayersChannel(Player p) {
        try {
            Object playerHandle = M_CRAFT_PLAYER__HANDLE.invoke(p);
            Object playerConnection = F_ENTITY_PLAYER__PLAYER_CONNECTION.getValue(playerHandle);
            Object networkManager = F_PLAYER_CONNECTION__NETWORK_MANAGER.getValue(playerConnection);
            return (Channel) F_NETWORK_MANAGER__CHANNEL.getValue(networkManager);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // * Set up interception to capture all currently online players as well
    // * as all new incoming channel connections
    //  * @param interceptor Consumer of internally created interception instances

    public void setupInterception(Consumer<Interceptor> interceptor) {
        attachInitializationListeners(interceptor);

        // Attach on all currently online players for convenience while developing and reloading
        for (Player p : Bukkit.getOnlinePlayers()) {
            Channel c = getPlayersChannel(p);

            if (c != null) {
                String playerName = p.getName();
                Interceptor inst = attachInterceptor(c, playerName);
                interceptorByPlayerName.put(playerName, inst);
                interceptor.accept(inst);
            }
        }
    }

    public void cleanupInterception() {
        detachInitializationListeners();
        detachInterceptors();
    }

    public Interceptor getPlayerInterceptor(Player p) {
        return interceptorByPlayerName.get(p.getName());
    }

    public String tryExtractName(Interceptor requester, Object packet) throws Exception {
        if (!C_PACKET_LOGIN.getClassType().isInstance(packet))
            return null;

        String name;

        if (F_PACKET_LOGIN__GAME_PROFILE != null) {
            name = ((GameProfile) F_PACKET_LOGIN__GAME_PROFILE.getValue(packet)).getName();
        } else {
            assert F_PACKET_LOGIN__NAME != null;
            name = (String) F_PACKET_LOGIN__NAME.getValue(packet);
        }

        if (name != null && !name.isEmpty())
            interceptorByPlayerName.put(name, requester);

        return name;
    }


    public void sendPacket(Object packet, Runnable completion, Object networkManager) throws Exception {
        senderManager.sendPacket(networkManager, packet, completion);
    }
}