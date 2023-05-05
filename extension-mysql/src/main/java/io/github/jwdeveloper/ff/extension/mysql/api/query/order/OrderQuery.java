package io.github.jwdeveloper.ff.extension.mysql.api.query.order;

import java.util.function.Consumer;

public interface OrderQuery<T>
{
    T orderBy(String ... columns);

    T orderByAsc(String ... columns);

    T orderByDesc(String ... columns);
}
