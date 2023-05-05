package io.github.jwdeveloper.ff.extension.mysql.implementation.query.where;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.group.SqlGroupBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.limit.SqlLimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.order.SqlOrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;

import java.util.function.Consumer;

public class SqlWhereBridge<T> extends SqlQuery<T> implements WhereBridge<T>, WhereQuery<WhereBridge<T>> {
    public SqlWhereBridge(QueryContext queryModel) {
        super(queryModel);
    }

    @Override
    public WhereBridge where(Consumer<WhereOptions> options) {
        var sqlOptions = new SqlWhereOptions();
        options.accept(sqlOptions);
        var model = sqlOptions.build();
        context.getQueryModel().setWhereModel(model);
        return this;
    }

    @Override
    public GroupBridge groupBy(String... columns) {
        return new SqlGroupBridge(context).groupBy(columns);
    }

    @Override
    public GroupBridge groupByHaving(String column, Consumer<WhereOptions> havingOptions) {
        return new SqlGroupBridge(context).groupByHaving(column,havingOptions);
    }

    @Override
    public LimitBridge limit(int offset, int count) {
        return new SqlLimitBridge(context).limit(offset, count);
    }

    @Override
    public LimitBridge limit(int count) {
        return new SqlLimitBridge(context).limit(count);
    }

    @Override
    public OrderBridge orderBy(String... columns) {
        return new SqlOrderBridge(context).orderBy(columns);
    }

    @Override
    public OrderBridge orderByAsc(String... columns) {
        return new SqlOrderBridge(context).orderByAsc(columns);
    }
    @Override
    public OrderBridge orderByDesc(String... columns) {
        return new SqlOrderBridge(context).orderByDesc(columns);
    }


}
