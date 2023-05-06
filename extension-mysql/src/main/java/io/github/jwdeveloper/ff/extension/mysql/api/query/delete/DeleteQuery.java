package io.github.jwdeveloper.ff.extension.mysql.api.query.delete;

import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;

import java.util.function.Consumer;

public interface DeleteQuery<T>
{
    T delete(Consumer<DeleteOptions> options);
}
