package io.github.jwdeveloper.ff.extension.mysql.api.query.table.column;

public interface ColumnOptions {

    ColumnOptions withForeignKey(String table, String column);

    ColumnOptions withColumnName(String name);

    ColumnOptions withPrimaryKey();

    ColumnOptions withAutoIncrement();

    ColumnOptions withRequired();

    ColumnOptions withType(String type);

    ColumnOptions withType(String type, int size);
}
