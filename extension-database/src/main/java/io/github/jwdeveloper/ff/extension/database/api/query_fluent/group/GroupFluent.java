package io.github.jwdeveloper.ff.extension.database.api.query_fluent.group;

import io.github.jwdeveloper.ff.extension.database.api.query_builder.order_builder.OrderBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.where_builders.WhereBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.QueryFluent;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.order.OrderFluent;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.where.WhereFluent;

public interface GroupFluent<T> extends QueryFluent<T>
{
   GroupFluent<T> column(String columns);

   WhereFluent<T> having();

   OrderFluent<T> orderBy();
}
