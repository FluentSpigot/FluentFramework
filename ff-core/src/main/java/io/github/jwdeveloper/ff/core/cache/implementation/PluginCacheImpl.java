package io.github.jwdeveloper.ff.core.cache.implementation;

import io.github.jwdeveloper.ff.core.cache.api.CacheObjectData;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
        cacheMap.remove(key);
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        cacheMap.put(key, new CacheObjectData(value, false, getCurrentTime(), duration));
    }

    @Override
    public void set(String key, Object value) {
        cacheMap.put(key, new CacheObjectData(value, true, null, null));
    }


    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    @Override
    public void close() throws IOException {
        cacheMap.clear();
    }
}
