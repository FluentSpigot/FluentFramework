package io.github.jwdeveloper.ff.api.tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjection;
import lombok.Getter;
import org.bukkit.event.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public abstract class FluentApiTest {
    @Getter
    private static FluentApiSpigot fluentApiMock;
    @Getter
    private static MockPlugin pluginMock;

    @Getter
    private static ServerMock serverMock;

    @Getter
    private static WorldMock worldMock;

    public PlayerMock getPlayer() {
        return serverMock.addPlayer();
    }


    public PluginManagerMock getPluginManager() {
        return serverMock.getPluginManager();
    }

    public abstract void onBuild(FluentApiSpigotBuilder fluentApiBuilder);

    public void sendMessage(String... message) {
        if (!MockBukkit.isMocked()) {
            return;
        }
        serverMock.getConsoleSender().sendMessage(message);
    }

    public <T extends Event> void invokeEvent(T event) {
        serverMock.getPluginManager().callEvent(event);
    }

    public boolean invokeCommand(String name, String... params) {
        var line = new StringBuilder(name);
        line.append(" ");
        for (var param : params) {
            line.append(param).append(" ");
        }
        return serverMock.dispatchCommand(serverMock.getConsoleSender(), line.toString());
    }

    public FluentInjection getContainer() {
        return fluentApiMock.container();
    }


    @BeforeEach
    public void before() throws Exception {
        if (MockBukkit.isMocked()) {
            MockBukkit.unmock();
        }
        serverMock = MockBukkit.mock();
        worldMock = serverMock.addSimpleWorld("world");
        serverMock.addWorld(worldMock);
        pluginMock = MockBukkit.createMockPlugin();
        var apiBuilder = FluentApiBuilder.create(pluginMock);
        onBuild(apiBuilder);
        fluentApiMock = apiBuilder.build();
        fluentApiMock.enable();
    }


    @AfterEach
    public void after() {
        if (!MockBukkit.isMocked()) {
            return;
        }
        fluentApiMock.disable();

        var commandSender = (ConsoleCommandSenderMock) serverMock.getConsoleSender();
        var message = StringUtils.EMPTY;
        do {
            System.out.println(message);
            message = commandSender.nextMessage();
        }
        while (message != null);

        MockBukkit.unmock();
    }

    public String loadResourceAsStream(Class<?> cls, String resourceName) {
        // Get the ClassLoader of the current class
        ClassLoader classLoader = cls.getClassLoader();

        // Get the InputStream of the resource file
        try (InputStream inputStream = classLoader.getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }

            // Read the InputStream into a String
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource as string", e);
        }
    }

}
