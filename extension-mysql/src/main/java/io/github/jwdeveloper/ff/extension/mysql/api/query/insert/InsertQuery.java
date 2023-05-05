package io.github.jwdeveloper.ff.extension.mysql.api.query.insert;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;

import java.util.function.Consumer;

public interface InsertQuery<T>
{
    T insert(Consumer<InsertOptions> options);
}
