package io.github.jwdeveloper.ff.extension.mysql.implementation.query.table;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.DropTableOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.TableBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.TableQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.create.CreateTableOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;
import java.util.function.Consumer;

public class SqlTableBridge<T> extends SqlQuery<T> implements TableBridge<T>, TableQuery<TableBridge<T>>
{

    public SqlTableBridge(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public TableBridge<T> createTable(Consumer<CreateTableOptions> options) {
        var sqlOptions = new SqlCreateTableOptions();
        options.accept(sqlOptions);
        var model = sqlOptions.build();
        context.getQueryModel().setTableCreateModel(model);
        return this;
    }

    @Override
    public TableBridge<T> dropTable(Consumer<DropTableOptions> options) {
        return null;
    }
}
