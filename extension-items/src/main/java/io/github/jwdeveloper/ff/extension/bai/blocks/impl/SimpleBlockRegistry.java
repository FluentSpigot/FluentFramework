package io.github.jwdeveloper.ff.extension.bai.blocks.impl;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockRegistry;

import java.util.List;
import java.util.Optional;

public class SimpleBlockRegistry implements FluentBlockRegistry {
    @Override
    public List<FluentBlock> findAll() {
        return null;
    }

    @Override
    public Optional<FluentBlock> findByName(String uniqueName) {
        return Optional.empty();
    }

    @Override
    public List<FluentBlock> findByTag(String tag) {
        return null;
    }

    @Override
    public void register(FluentBlock item) {

    }

    @Override
    public void unregister(FluentBlock item) {

    }

    @Override
    public void reset() {

    }
}
