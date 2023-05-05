package io.github.jwdeveloper.ff.extension.mysql.implementation.query.update;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.select.SqlSelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.where.SqlWhereBridge;

import java.util.Map;
import java.util.function.Consumer;

public class SqlUpdateBridge<T> extends SqlQuery<T> implements UpdateBridge<T>, UpdateQuery<UpdateBridge<T>> {

    public SqlUpdateBridge(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public WhereBridge<T> where(Consumer<WhereOptions> options) {
        return new SqlWhereBridge(context).where(options);
    }


    @Override
    public UpdateBridge<T> update(Consumer<UpdateOptions> options) {
        var sqlOptions = new SqlUpdateOptions();
        options.accept(sqlOptions);
        var model = sqlOptions.build();
        context.getQueryModel().setUpdateModel(model);
        return this;
    }
}
