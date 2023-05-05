package io.github.jwdeveloper.ff.extension.mysql.api.query.where;

import java.util.function.Consumer;

public interface WhereQuery<T>
{
    T where(Consumer<WhereOptions> options);
}
