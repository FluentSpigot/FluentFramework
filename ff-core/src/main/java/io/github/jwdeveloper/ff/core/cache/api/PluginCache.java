package io.github.jwdeveloper.ff.core.cache.api;

import java.io.Closeable;
import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface PluginCache extends Closeable {
    boolean contains(String key);

    Object get(String key);

    <T> T getOrCreate(String key, Supplier<T> creator);

    <T> boolean ifPresent(String key, Consumer<T> action);

    <T> boolean removeIfPresent(String key, Consumer<T> action);

    <T> List<T> findAll(Function<String, Boolean> validation, Consumer<T> action);

    <T> List<T> findAll(Function<String, Boolean> validation);

    void refresh(String key);

    void remove(String key);

    void set(String key, Object value, Duration duration);

    String set(Object value);

    void set(String key, Object value);

    void dump();
}
