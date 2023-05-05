package io.github.jwdeveloper.ff.extension.mysql.implementation.query.delete;


import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.delete.DeleteBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.delete.DeleteQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.select.SqlSelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.where.SqlWhereBridge;

import java.util.function.Consumer;

public class SqlDeleteBridge<T> extends SqlQuery<T> implements DeleteBridge<T>, DeleteQuery<DeleteBridge<T>>
{
    public SqlDeleteBridge(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public DeleteBridge<T> delete(Consumer<SelectOptions> options) {
        var sqlOptions = new SqlSelectOptions("DELETE");
        options.accept(sqlOptions);
        var model = sqlOptions.build();
        context.getQueryModel().setSelectModel(model);
        return this;
    }

    @Override
    public WhereBridge<T> where(Consumer<WhereOptions> options) {
        return new SqlWhereBridge(context).where(options);
    }
}
