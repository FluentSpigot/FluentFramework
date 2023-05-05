package io.github.jwdeveloper.ff.extension.mysql.api.query.group;

import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereOptions;

import java.util.function.Consumer;

public interface GroupQuery<T>
{
    T groupBy(String ... columns);

    T groupByHaving(String column, Consumer<WhereOptions> havingOptions);
}
