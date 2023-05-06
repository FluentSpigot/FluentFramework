package io.github.jwdeveloper.ff.extension.mysql.implementation.query.table;

import io.github.jwdeveloper.ff.extension.mysql.api.query.table.column.ColumnModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.column.ColumnOptions;

public class SqlColumnOptions implements ColumnOptions {
    private final ColumnModel columnModel;

    public SqlColumnOptions()
    {
        columnModel = new ColumnModel();
    }

    @Override
    public ColumnOptions withColumnName(String name) {
        columnModel.setColumnName(name);
        return this;
    }

    @Override
    public ColumnOptions withPrimaryKey() {
        columnModel.setPrimaryKey(true);
        return this;
    }

    @Override
    public ColumnOptions withForeignKey(String table, String column) {
        columnModel.setForeignKey(true);
        columnModel.setForeignTable(table);
        columnModel.setForeignColumn(column);
        return this;
    }

    @Override
    public ColumnOptions withAutoIncrement() {
        columnModel.setAutoIncrement(true);
        return this;
    }

    @Override
    public ColumnOptions withRequired() {
        columnModel.setRequired(true);
        return this;
    }

    @Override
    public ColumnOptions withType(String type) {
        columnModel.setColumnType(type);
        return this;
    }

    @Override
    public ColumnOptions withType(String type, int size) {
        columnModel.setColumnType(type+"("+size+")");
        return this;
    }

    public ColumnModel build()
    {
        return columnModel;
    }
}
