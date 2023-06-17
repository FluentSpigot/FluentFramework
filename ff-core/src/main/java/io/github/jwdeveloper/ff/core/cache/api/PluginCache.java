package io.github.jwdeveloper.ff.core.cache.api;

import java.io.Closeable;
import java.time.Duration;

public interface PluginCache extends Closeable
{
    boolean contains(String key);

    Object get(String key);

    void refresh(String key);

    void remove(String key);

    void set(String key, Object value, Duration duration);

    void set(String key, Object value);
}
