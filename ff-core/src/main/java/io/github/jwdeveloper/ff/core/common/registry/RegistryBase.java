package io.github.jwdeveloper.ff.core.common.registry;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RegistryBase<T> implements Registry<T> {
    protected final Set<T> items = new HashSet<>();

    @Override
    public List<T> findAll() {
        return items.stream().toList();
    }

    @Override
    public  Optional<T> findByName(String uniqueName)
    {
        return Optional.empty();
    }
    @Override
    public List<T> findByTag(String tag)
    {
        return List.of();
    }

    @Override
    public void register(T item) {
        items.add(item);
    }

    @Override
    public void unregister(T item) {
        items.remove(item);
    }

    @Override
    public void reset() {
        items.clear();
    }
}
