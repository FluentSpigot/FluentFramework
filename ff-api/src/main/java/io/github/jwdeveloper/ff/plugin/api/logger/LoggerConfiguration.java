package io.github.jwdeveloper.ff.plugin.api.logger;

import java.util.function.Consumer;

public interface LoggerConfiguration
{
     void configureLogger();

     void configurePlayerLogger(Consumer<PlayerLoggerConfig> config);
}
