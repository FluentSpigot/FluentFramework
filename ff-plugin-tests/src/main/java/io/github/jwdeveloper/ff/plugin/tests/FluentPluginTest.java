package io.github.jwdeveloper.ff.plugin.tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjection;
import lombok.Getter;
import org.bukkit.event.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Objects;

public abstract class FluentPluginTest
{
    @Getter
    private static FluentApiSpigot fluentApiMock;
    @Getter
    private static MockPlugin pluginMock;

    @Getter
    private static ServerMock serverMock;

    @Getter
    private static WorldMock worldMock;


    public Class<?> getJarScannerClass() {
        return this.getClass();
    }
    public String getPluginName() {return StringUtils.EMPTY;};
    public PlayerMock getPlayer()
    {
        return  serverMock.addPlayer();
    }

    public abstract Class<?> mainPluginClass();

    public PluginManagerMock getPluginManager()
    {
        return serverMock.getPluginManager();
    }

    public abstract void onFluentPluginBuild(FluentPluginBuilder builder);

    public void sendMessage(String ... message)
    {
        if(!MockBukkit.isMocked())
        {
            return;
        }
        serverMock.getConsoleSender().sendMessage(message);
    }

    public <T extends Event> void invokeEvent(T event)
    {
        serverMock.getPluginManager().callEvent(event);
    }

    public boolean invokeCommand(String name, String... params)
    {
        var line = new StringBuilder(name);
        line.append(" ");
        for(var param : params)
        {
            line.append(param).append(" ");
        }
        return serverMock.dispatchCommand(serverMock.getConsoleSender(), line.toString());
    }

    public FluentInjection getInjection()
    {
        return fluentApiMock.container();
    }


    @BeforeEach
    public void before() throws Exception {
        if(MockBukkit.isMocked())
        {
            MockBukkit.unmock();
        }
        serverMock = MockBukkit.mock();
        worldMock = serverMock.addSimpleWorld("world");
        serverMock.addWorld(worldMock);

        if(!Objects.equals(getPluginName(), StringUtils.EMPTY))
        {
            pluginMock = MockBukkit.createMockPlugin(getPluginName());
        }
        else
        {
            pluginMock = MockBukkit.createMockPlugin();
        }

        FluentLogger.setLogger("test");
        var builder = FluentPlugin.initialize(pluginMock);
        builder.withExtension(builder1 ->
        {
            builder1.jarScanner().attacheAllClassesFromPackage(mainPluginClass());
        });
        onFluentPluginBuild(builder);
        fluentApiMock = builder.tryCreate();
        fluentApiMock.enable();
    }


    @AfterEach
    public void after()
    {
        if(!MockBukkit.isMocked())
        {
            return;
        }
        if(fluentApiMock != null)
        {
            fluentApiMock.disable();

            var commandSender = (ConsoleCommandSenderMock)serverMock.getConsoleSender();
            var message = StringUtils.EMPTY;
            do
            {
                System.out.println(message);
                message =  commandSender.nextMessage();
            }
            while (message != null);
        }
        MockBukkit.unmock();
    }




}