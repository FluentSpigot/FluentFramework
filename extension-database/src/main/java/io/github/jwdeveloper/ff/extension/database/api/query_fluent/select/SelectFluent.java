package io.github.jwdeveloper.ff.extension.database.api.query_fluent.select;

import io.github.jwdeveloper.ff.extension.database.api.query_abstract.select.AbstractSelectQuery;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.where.WhereFluent;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.QueryFluent;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.order.OrderFluent;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.order.OrderFluentBridge;

public interface SelectFluent<T> extends AbstractSelectQuery<SelectFluent<T>>, OrderFluentBridge<T>, QueryFluent<T>
{
     SelectFluent<T> join(Class<?> foreignTable);

     WhereFluent<T> where();

     OrderFluent<T> orderBy();
}
