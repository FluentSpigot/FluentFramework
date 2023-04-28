package io.github.jwdeveloper.ff.extension.database.api.query_builder.where_builders;

import io.github.jwdeveloper.ff.extension.database.api.query_abstract.AbstractQuery;

public interface WhereBuilderBridge extends AbstractQuery
{
    WhereBuilder where();
}
