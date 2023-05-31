package io.github.jwdeveloper.ff.core.common.logger;

public interface PluginLogger {
    void info(Object... messages);

    void success(Object... messages);

    void warning(Object... messages);

    void error(String message);

    void error(String message, Throwable throwable);
}
