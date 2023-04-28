package io.github.jwdeveloper.ff.extension.database.api.query_builder.insert_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_abstract.AbstractQuery;

public interface InsertBuilderValue extends AbstractQuery
{
    public InsertBuilderValue values(Object... values);
}
