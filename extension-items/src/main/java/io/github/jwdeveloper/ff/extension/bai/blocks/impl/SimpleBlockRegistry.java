package io.github.jwdeveloper.ff.extension.bai.blocks.impl;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleBlockRegistry implements FluentBlockRegistry {

    private final List<FluentBlock> blocks = new ArrayList<>();

    @Override
    public List<FluentBlock> findAll() {
        return blocks;
    }

    @Override
    public Optional<FluentBlock> findByName(String uniqueName) {
        return blocks.stream().filter(e -> e.fluentItem().getSchema().getName().equals(uniqueName)).findFirst();
    }

    @Override
    public List<FluentBlock> findByTag(String tag) {
        return blocks.stream().filter(e -> e.fluentItem().getSchema().getTag().equals(tag)).toList();
    }

    @Override
    public void register(FluentBlock item) {
        blocks.add(item);
    }

    @Override
    public void unregister(FluentBlock item) {
        blocks.remove(item);
    }

    @Override
    public void reset() {
        blocks.clear();
    }
}
