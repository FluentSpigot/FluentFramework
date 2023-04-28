package io.github.jwdeveloper.ff.extension.database.api.query_fluent.select;

import io.github.jwdeveloper.ff.extension.database.api.query_fluent.QueryFluent;

public interface SelectFluentBridge<T> extends QueryFluent<T>, SelectFluent<T>
{
    public SelectFluent<T> columns(String ... columns);
}
