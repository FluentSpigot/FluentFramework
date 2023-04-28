package io.github.jwdeveloper.ff.extension.database.api.query_builder.where_builders;

import io.github.jwdeveloper.ff.extension.database.api.query_abstract.AbstractQuery;
import io.github.jwdeveloper.ff.extension.database.api.query_abstract.where.AbstractWhereQuery;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.group_builder.GroupBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.order_builder.OrderBuilderBridge;

public interface WhereBuilder extends AbstractWhereQuery<WhereBuilder>, AbstractQuery, OrderBuilderBridge
{


    public GroupBuilder groupBy();
}
