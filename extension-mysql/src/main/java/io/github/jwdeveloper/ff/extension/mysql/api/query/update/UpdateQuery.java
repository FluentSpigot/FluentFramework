package io.github.jwdeveloper.ff.extension.mysql.api.query.update;

import java.util.function.Consumer;

public interface UpdateQuery<T>
{
    T update(Consumer<UpdateOptions> options);
}
