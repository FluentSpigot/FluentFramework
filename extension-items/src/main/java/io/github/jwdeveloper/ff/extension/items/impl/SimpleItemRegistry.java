package io.github.jwdeveloper.ff.extension.items.impl;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemRegistry;

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
    public void register(FluentItem fluentItem) {

        var idName = fluentItem.getSchema().getName();
        itemMap.put(idName, fluentItem);
        tagIndexes.computeIfAbsent(fluentItem.getSchema().getTag(), s -> new ArrayList<>());
        tagIndexes.get(fluentItem.getSchema().getTag()).add(fluentItem);
    }

    @Override
    public void reset() {
        itemMap.clear();
    }
}
