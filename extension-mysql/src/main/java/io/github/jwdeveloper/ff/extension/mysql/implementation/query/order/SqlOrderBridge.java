package io.github.jwdeveloper.ff.extension.mysql.implementation.query.order;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.limit.SqlLimitBridge;

public class SqlOrderBridge<T> extends SqlQuery<T> implements OrderBridge<T>, OrderQuery<OrderBridge<T>> {

    public SqlOrderBridge(QueryContext queryModel) {
        super(queryModel);
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
        context.getQueryModel().setOrderModel(new OrderModel(columns, true));
        return this;
    }

    @Override
    public OrderBridge orderByAsc(String... columns) {
        context.getQueryModel().setOrderModel(new OrderModel(columns, true));
        return this;
    }

    @Override
    public OrderBridge orderByDesc(String... columns) {
        context.getQueryModel().setOrderModel(new OrderModel(columns, false));
        return this;
    }
}
