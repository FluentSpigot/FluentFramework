package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.search;

import java.util.List;

public interface SearchFilter<T> {
    List<T> onFilter(List<T> content, String query);
}
