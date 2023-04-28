package io.github.jwdeveloper.ff.extension.database.api.query_abstract.select;

import io.github.jwdeveloper.ff.extension.database.api.query_abstract.AbstractQuery;

public interface AbstractSelectQuery<T> extends AbstractQuery
{
    T count(String column);

    T avg(String column);

    T sum(String column);
}
