package io.github.jwdeveloper.ff.core.cache.implementation;

import io.github.jwdeveloper.ff.core.cache.api.CacheObjectData;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import org.bukkit.entity.Entity;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PluginCacheImpl implements PluginCache {
    private final Map<String, CacheObjectData> cacheMap = new HashMap<>();

    @Override
    public boolean contains(String key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public Object get(String key) {
        if (!contains(key)) {
            throw new RuntimeException("Cache not contains data for key" + key);
        }
        var data = cacheMap.get(key);
        if (data.isForever()) {
            return data.getObject();
        }

        var currentTime = getCurrentTime();
        var cacheExpires = data.getCreatedAt().plus(data.getDuration());
        if (currentTime.isAfter(cacheExpires)) {
            remove(key);
        }
        return data.getObject();
    }

    @Override
    public <T> T getOrCreate(String key, Supplier<T> creator) {
        if (contains(key)) {
            return (T) get(key);
        }

        var obj = creator.get();
        set(key, obj);
        return (T) obj;
    }

    @Override
    public <T> boolean ifPresent(String key, Consumer<T> action) {
        if (!contains(key)) {
            return false;
        }
        var obj = get(key);
        action.accept((T) obj);
        return true;
    }

    @Override
    public <T> boolean removeIfPresent(String key, Consumer<T> action) {
        if (ifPresent(key, action)) {
            remove(key);
            return true;
        }
        return false;
    }


    @Override
    public <T> List<T> findAll(Function<String, Boolean> validation, Consumer<T> action) {
        var result = new ArrayList<T>();
        for (var entry : cacheMap.entrySet()) {
            if (!validation.apply(entry.getKey())) {
                continue;
            }
            var value = (T) get(entry.getKey());
            action.accept(value);
            result.add(value);
        }
        return result;
    }

    @Override
    public <T> List<T> findAll(Function<String, Boolean> validation) {
        return findAll(validation,t -> {});
    }

    @Override
    public void refresh(String key) {
        if (!contains(key)) {
            return;
        }
        var data = cacheMap.get(key);
        data.setCreatedAt(getCurrentTime());
    }

    @Override
    public void remove(String key) {
        if (!contains(key)) {
            return;
        }

        var value = cacheMap.get(key);
        var obj = value.getObject();
        if(obj instanceof SimpleTaskTimer taskTimer)
        {
            taskTimer.stop();
        }
        if(obj instanceof Entity taskTimer)
        {
            taskTimer.remove();
        }


        cacheMap.remove(key);
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        cacheMap.put(key, new CacheObjectData(value, false, getCurrentTime(), duration));
    }

    @Override
    public String set(Object value)
    {
        var id = UUID.randomUUID().toString();
        set(id,value);
        return id;
    }

    @Override
    public void set(String key, Object value) {
        cacheMap.put(key, new CacheObjectData(value, true, null, null));
    }

    @Override
    public void dump() {


        cacheMap.forEach((a, b) ->
        {
            FluentLogger.LOGGER.info(a, b);
        });
    }


    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    @Override
    public void close() throws IOException {

        for(var entry : cacheMap.entrySet())
        {
            remove(entry.getKey());
        }
        cacheMap.clear();
    }
}
