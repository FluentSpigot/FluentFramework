package io.github.jwdeveloper.ff.extension.database.mysql.query_builder.bridge_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_builder.bridge_builder.BridgeBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.group_builder.GroupBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.join_builder.JoinBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.order_builder.OrderBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.where_builders.WhereBuilder;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.where_builders.WhereBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.QueryBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.group_builder.GroupBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.join_builder.JoinBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.order_builder.OrderBuilderImpl;

public class BridgeBuilderImpl extends QueryBuilderImpl implements BridgeBuilder
{
    public BridgeBuilderImpl(StringBuilder query) {
        super(query);
    }

    public JoinBuilder join()
    {
        return new JoinBuilderImpl(query);
    }

    public WhereBuilder where()
    {
        return new WhereBuilderImpl(query).where();
    }

    public GroupBuilder groupBy()
    {
        return new GroupBuilderImpl(query).groupBy();
    }

    public OrderBuilder orderBy()
    {
        return new OrderBuilderImpl(query).orderBy();
    }
}
