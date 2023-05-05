package io.github.jwdeveloper.ff.extension.mysql.implementation.query.limit;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;

public class SqlLimitBridge<T> extends SqlQuery<T> implements LimitBridge<T>, LimitQuery<LimitBridge<T>> {

    public SqlLimitBridge(QueryContext queryModel) {
        super(queryModel);
    }

    @Override
    public LimitBridge limit(int offset, int count) {
        context.getQueryModel().setLimitModel(new LimitModel(offset, count));
        return this;
    }

    @Override
    public LimitBridge limit(int count) {
        context.getQueryModel().setLimitModel(new LimitModel(0, count));
        return this;
    }
}
