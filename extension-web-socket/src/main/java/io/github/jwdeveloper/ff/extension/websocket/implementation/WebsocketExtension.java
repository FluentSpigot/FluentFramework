package io.github.jwdeveloper.ff.extension.websocket.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.extension.websocket.api.FluentWebsocket;
import io.github.jwdeveloper.ff.extension.websocket.api.FluentWebsocketPacket;
import io.github.jwdeveloper.ff.extension.websocket.api.data.WebsocketOptions;
import io.github.jwdeveloper.ff.extension.websocket.implementation.config.WebSocketConfig;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;

public class WebsocketExtension implements FluentApiExtension {

    private final Consumer<WebsocketOptions> consumer;
    private WebsocketOptions options;

    public WebsocketExtension(Consumer<WebsocketOptions> optionsConsumer) {
        consumer = optionsConsumer;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        options = new WebsocketOptions();
        consumer.accept(options);

        builder.bindToConfig(WebSocketConfig.class, options.getConfigPath());
        builder.container().registerSingletonList(FluentWebsocketPacket.class);
        builder.container().registerSingleton(FluentWebsocket.class, container ->
        {
            var webSocketConfig = (WebSocketConfig)container.find(WebSocketConfig.class);
            var logger = builder.logger();
            if (!webSocketConfig.isRunServer())
            {
                logger.info("Websocket is disabled in order to enable it set ", options.getConfigPath() + ".enable", " to true in config.yml");
                return null;
            }
            try
            {
                return new FluentWebsocketImpl(webSocketConfig.getPort(), logger, options);
            } catch (Exception e) {
                throw new RuntimeException("Websocket error, check if port is open and free to use, more help here: ", e);
            }
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var configOptions = fluentAPI.container().findInjection(FluentConfigFile.class, WebSocketConfig.class);
        var config = (WebSocketConfig)configOptions.get();
        if(!config.isRunServer())
        {
            return;
        }

        if (StringUtils.isNullOrEmpty(config.getServerIp()))
        {
            config.setServerIp(getServerPublicIP());
            configOptions.save();
        }

        var webSocket = (FluentWebsocketImpl) fluentAPI.container().findInjection(FluentWebsocket.class);
        var packets = fluentAPI.container().findAllByInterface(FluentWebsocketPacket.class);
        webSocket.setServerIp(config.getServerIp());
        webSocket.registerPackets(packets);
        webSocket.start();
        fluentAPI.logger().info("Websocket runs on:", webSocket.getServerIp() + ":" + webSocket.getPort());
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
        var config = fluentAPI.container().findInjection(WebSocketConfig.class);
        if(!config.isRunServer())
        {
            return;
        }
        var webSocket = fluentAPI.container().findInjection(FluentWebsocket.class);
        webSocket.stop();
    }

    private String getServerPublicIP() throws IOException {
        var url = new URL("http://checkip.amazonaws.com/");
        var br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br.readLine();
    }


    @Override
    public String getName() {
        return "websocket-server";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
