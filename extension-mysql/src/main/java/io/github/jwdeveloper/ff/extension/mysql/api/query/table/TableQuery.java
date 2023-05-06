package io.github.jwdeveloper.ff.extension.mysql.api.query.table;

import io.github.jwdeveloper.ff.extension.mysql.api.query.table.create.CreateTableOptions;

import java.util.function.Consumer;

public interface TableQuery<T>
{
    T createTable(Consumer<CreateTableOptions> options);

    T dropTable(Consumer<DropTableOptions> options);
}
