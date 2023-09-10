package io.github.jwdeveloper.spigot.ff.extensions.base;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FluentHttpServer
{
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Create a context for handling requests to the root path "/"


        // Start the server
        server.start();

    }
}
