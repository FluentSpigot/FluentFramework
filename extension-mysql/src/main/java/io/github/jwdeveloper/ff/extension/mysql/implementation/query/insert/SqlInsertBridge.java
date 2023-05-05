package io.github.jwdeveloper.ff.extension.mysql.implementation.query.insert;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.select.SqlSelectOptions;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class SqlInsertBridge<T> extends SqlQuery<T> implements InsertBridge<T>, InsertQuery<InsertBridge<T>> {

    public SqlInsertBridge(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public InsertBridge<T> insert(Consumer<InsertOptions> options)
    {
        var sqlOptions = new SqlInsertOptions();
        options.accept(sqlOptions);
        var model = sqlOptions.build();
        context.getQueryModel().setInsertModel(model);
        return this;
    }
}
