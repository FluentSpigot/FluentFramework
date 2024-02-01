package io.github.jwdeveloper.ff.extension.items.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FluentItemRegistry {
    List<FluentItem> findAll();

    Optional<FluentItem> findByName(String uniqueName);

    List<FluentItem> findByTag(String tag);

    void register(FluentItem fluentItem);

    void reset();
}
