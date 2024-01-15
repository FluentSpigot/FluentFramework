package io.github.jwdeveloper.ff.extension.protocol.old;


import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.reflect.api.models.ClassModel;
import io.github.jwdeveloper.reflect.api.models.FieldModel;
import io.github.jwdeveloper.reflect.api.models.MethodModel;
import io.github.jwdeveloper.reflect.implementation.FluentReflect;
import io.github.jwdeveloper.reflect.implementation.models.JavaFieldModel;
import io.netty.channel.Channel;
import lombok.Getter;

@Injection
@Getter
public class PacketReflections {


    private ClassModel entityPlayerClass;

    private ClassModel playerConnectionClass;

    private ClassModel networkManagerClass;

    private MethodModel getPlayerHandle;

    private JavaFieldModel getConnection;

    private JavaFieldModel getManager;

    private JavaFieldModel getChannel;


    private ClassModel minecraftServerClass;
    private ClassModel serverConnectionClass;
    private JavaFieldModel getMinecraftServer;
    private JavaFieldModel getServerConnection;


    private final FluentReflect reflect;

    public PacketReflections(FluentReflect fluentReflect) {
        this.reflect = fluentReflect;
    }


    public void load(String version)
    {
        entityPlayerClass = reflect.findClass()
                .forAnyVersion(finder ->
                {
                    finder.withName("net.minecraft.server.level.EntityPlayer");
                }).find();

        playerConnectionClass = reflect.findClass()
                .forAnyVersion(finder ->
                {
                    finder.withName("net.minecraft.server.network.PlayerConnection");
                }).find();

        networkManagerClass = reflect.findClass()
                .forAnyVersion(finder ->
                {
                    finder.withName("net.minecraft.network.NetworkManager");
                }).find();

        //   private static final Reflection.MethodInvoker getPlayerHandle = Reflection.getMethod("{obc}.entity.CraftPlayer", "getHandle");


        getConnection = entityPlayerClass.findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(playerConnectionClass.getClass());
                }).find();

        getManager = playerConnectionClass.findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(networkManagerClass.getClass());
                }).find();

        getChannel = networkManagerClass.findField()
                .forAnyVersion(finder ->
                {
                    finder.withType(Channel.class);
                }).find();


        minecraftServerClass = reflect.findClass()
                .forAnyVersion(finder ->
                {
                    finder.withName("net.minecraft.server.MinecraftServer");
                }).find();

        serverConnectionClass = reflect.findClass()
                .forAnyVersion(finder ->
                {
                    finder.withName("net.minecraft.server.network.ServerConnection");
                }).find();

        getMinecraftServer = minecraftServerClass.findField().forAnyVersion(finder ->
        {
           // finder.with("org.bukkit.craftbukkit."+version+".CraftServer");
        }).find();



        /*
          private static final Class<Object> minecraftServerClass = Reflection.getUntypedClass("{nms}.MinecraftServer", "net.minecraft.server.MinecraftServer");
    private static final Class<Object> serverConnectionClass = Reflection.getUntypedClass("{nms}.ServerConnection", "net.minecraft.server.network.ServerConnection");
    private static final Reflection.FieldAccessor<Object> getMinecraftServer = Reflection.getField("{obc}.CraftServer", minecraftServerClass, 0);
    private static final Reflection.FieldAccessor<Object> getServerConnection = Reflection.getField(minecraftServerClass, serverConnectionClass, 0);
         */

    }

}
