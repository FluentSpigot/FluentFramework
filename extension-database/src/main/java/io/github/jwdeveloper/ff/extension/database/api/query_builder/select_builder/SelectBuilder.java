package io.github.jwdeveloper.ff.extension.database.api.query_builder.select_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_abstract.select.AbstractSelectQuery;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.bridge_builder.BridgeBuilder;

public interface SelectBuilder extends AbstractSelectQuery<SelectBuilder>
{
     SelectBuilder columns(String... columns);

     <T> BridgeBuilder from(Class<T> table);

     BridgeBuilder from(String table);
}
