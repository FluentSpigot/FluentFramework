package io.github.jwdeveloper.ff.extension.bai.common.api;

import java.util.List;
import java.util.Optional;

public interface Registry<T> {
    List<T> findAll();

    Optional<T> findByName(String uniqueName);

    List<T> findByTag(String tag);

    void register(T item);
    void unregister(T item);

    void reset();
}
