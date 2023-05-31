package io.github.jwdeveloper.ff.extension.websocket.implementation.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;


@Data
public class WebSocketConfig
{
    @YamlSection(description = """
            ! Make sure that port is open
            ! When you have server on hosting, generate new port on the hosting panel
            """)
    private int port = 443;


    @YamlSection(name = "server-ip", description =
            """
                    Set own IP for websocket, by default plugin use IP of your server
                      ! When you are using proxy set here proxy IP
                      ! When you are running plugin locally on your PC, set 'localhost'
                      ! When default IP not works try use IP that you are using in minecraft server list
                    """)
    private String serverIp;

    @YamlSection(name = "enable", description =
            """
                    When set to false websocket will not run
                    """)
    private boolean runServer = true;


}
