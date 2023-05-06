package io.github.jwdeveloper.ff.extension.mysql.implementation.query.table;

import io.github.jwdeveloper.ff.extension.mysql.api.query.table.column.ColumnModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.column.ColumnOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.create.CreateTableOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.create.TableCreateModel;

import java.util.function.Consumer;

public class SqlCreateTableOptions implements CreateTableOptions {

    private final TableCreateModel tableCreateModel;

    public SqlCreateTableOptions() {
        tableCreateModel = new TableCreateModel();
    }


    @Override
    public CreateTableOptions withTableName(String tableName) {
        tableCreateModel.setTableName(tableName);
        return this;
    }

    @Override
    public CreateTableOptions withColumn(String columnName) {
        var columnModel = new ColumnModel();
        columnModel.setColumnName(columnName);

        tableCreateModel.getColumnModels().add(columnModel);
        return this;
    }

    @Override
    public CreateTableOptions withColumn(String columnName, String columnType) {
        var columnModel = new ColumnModel();
        columnModel.setColumnType(columnType);
        columnModel.setColumnName(columnName);

        tableCreateModel.getColumnModels().add(columnModel);
        return this;
    }

    @Override
    public CreateTableOptions withColumn(Consumer<ColumnOptions> columnBuilder) {
        var options = new SqlColumnOptions();
        columnBuilder.accept(options);
        var model = options.build();
        tableCreateModel.getColumnModels().add(model);
        return this;
    }

    @Override
    public CreateTableOptions withColumn(ColumnModel columnModel) {
        tableCreateModel.getColumnModels().add(columnModel);
        return this;
    }

    public TableCreateModel build() {
        return tableCreateModel;
    }
}
