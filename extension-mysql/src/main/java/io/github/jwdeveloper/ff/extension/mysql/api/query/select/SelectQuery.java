package io.github.jwdeveloper.ff.extension.mysql.api.query.select;

import java.util.function.Consumer;

public interface SelectQuery<T>
{
    T select(Consumer<SelectOptions> options);
}
