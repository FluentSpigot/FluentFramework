package io.github.jwdeveloper.ff.extension.database.mysql.query_builder.order_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_builder.order_builder.OrderBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.order_builder.OrderBuilderBridge;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.QueryBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.SqlSyntaxUtils;

public class OrderBuilderImpl extends QueryBuilderImpl implements OrderBuilderBridge, OrderBuilder {
    public OrderBuilderImpl(StringBuilder query) {
        super(query);
    }

    public OrderBuilder orderBy()
    {
        query.append(SqlSyntaxUtils.ORDER_BY);
        return this;
    }

    @Override
    public OrderBuilder desc(String table) {
        query.append(table).append(SqlSyntaxUtils.DESC);
        return this;
    }

    @Override
    public OrderBuilder asc(String table) {
        query.append(table).append(SqlSyntaxUtils.ASC);
        return this;
    }
}
