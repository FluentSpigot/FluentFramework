package io.github.jwdeveloper.ff.core.logger.plugin;

public interface PluginLogger {
    void info(Object... messages);

    void success(Object... messages);

    void warning(Object... messages);

    void error(Object... message);

    void error(String message, Throwable throwable);

    void setActive(boolean value);

    void disable();

    void enable();
}
