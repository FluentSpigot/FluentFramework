package io.github.jwdeveloper.ff.extension.bai.items.impl;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemRegistry;

import java.util.*;

public class SimpleItemRegistry implements FluentItemRegistry {
    private final Map<String, FluentItem> itemMap = new TreeMap<>();
    private final Map<String, List<FluentItem>> tagIndexes = new HashMap<>();

    @Override
    public List<FluentItem> findAll() {
        return itemMap.values().stream().toList();
    }

    @Override
    public Optional<FluentItem> findByName(String uniqueName) {
        return Optional.ofNullable(itemMap.get(uniqueName));
    }

    @Override
    public List<FluentItem> findByTag(String tag) {
        return tagIndexes.getOrDefault(tag, List.of());
    }

    @Override
    public void register(FluentItem item) {

        var idName = item.getSchema().getName();
        itemMap.put(idName, item);
        tagIndexes.computeIfAbsent(item.getSchema().getTag(), s -> new ArrayList<>());
        tagIndexes.get(item.getSchema().getTag()).add(item);
    }

    @Override
    public void unregister(FluentItem item) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void reset() {
        itemMap.clear();
    }
}
