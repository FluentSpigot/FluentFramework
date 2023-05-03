package io.github.jwdeveloper.ff.extension.database.api.query_builder.order_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_abstract.AbstractQuery;
import io.github.jwdeveloper.ff.extension.database.api.query_abstract.order.AbstractOrderQuery;

public interface OrderBuilder extends AbstractQuery, AbstractOrderQuery<OrderBuilder>
{
     OrderBuilder desc(String table);

     OrderBuilder asc(String table);
}
