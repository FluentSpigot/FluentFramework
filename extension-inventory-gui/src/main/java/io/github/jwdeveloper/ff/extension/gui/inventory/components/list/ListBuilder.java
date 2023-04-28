package io.github.jwdeveloper.ff.extension.gui.inventory.components.list;

import io.github.jwdeveloper.ff.extension.gui.inventory.components.pagination.PaginationBuilder;
import io.github.jwdeveloper.ff.extension.gui.inventory.components.search.SearchBuilder;

import java.util.function.Consumer;

public class ListBuilder<T>
{
    PaginationBuilder<T> paginationBuilder;
    SearchBuilder searchBuilder;

    public ListBuilder()
    {

    }


    public ListBuilder<T> configPagination(Consumer<PaginationBuilder<T>> pagination)
    {
        return this;
    }

    public ListBuilder<T> configSearch(Consumer<PaginationBuilder<T>> pagination)
    {
        return this;
    }

    public ListComponent<T> build()
    {
        return new ListComponent<>(paginationBuilder, searchBuilder);
    }
}
