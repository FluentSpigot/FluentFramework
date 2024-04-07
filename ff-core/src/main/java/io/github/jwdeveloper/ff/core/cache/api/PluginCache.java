package io.github.jwdeveloper.ff.core.cache.api;

import java.io.Closeable;
import java.time.Duration;
import java.util.function.Supplier;

public interface PluginCache extends Closeable {
    boolean contains(String key);

    Object get(String key);

    <T> T getOrCreate(String key, Supplier<T> creator);

    void refresh(String key);

    void remove(String key);

    void set(String key, Object value, Duration duration);

    void set(String key, Object value);
}
