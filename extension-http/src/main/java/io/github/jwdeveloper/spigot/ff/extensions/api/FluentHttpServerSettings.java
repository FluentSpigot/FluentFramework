package io.github.jwdeveloper.spigot.ff.extensions.api;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Setter
public class FluentHttpServerSettings {
    String ip;
    int port;
    boolean useSll;

    int tokenTimeout;
    String tokenSecret;


    FluentHttpServerBuildAction onBuilding;
}
