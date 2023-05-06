package io.github.jwdeveloper.ff.extension.mysql.api.query.table.create;

import io.github.jwdeveloper.ff.extension.mysql.api.query.table.column.ColumnModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.column.ColumnOptions;

import java.util.function.Consumer;

public interface CreateTableOptions
{
    CreateTableOptions withTableName(String tableName);

    CreateTableOptions withColumn(String columnName);

    CreateTableOptions withColumn(String columnName, String columnType);

    CreateTableOptions withColumn(Consumer<ColumnOptions> columnBuilder);

    CreateTableOptions withColumn(ColumnModel columnModel);
}
