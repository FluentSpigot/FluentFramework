package io.github.jwdeveloper.ff.extension.mysql.implementation.query;

import io.github.jwdeveloper.ff.extension.mysql.api.query.AbstractQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;

import java.util.List;
import java.util.Optional;

public class SqlQuery<T> implements AbstractQuery<T> {
    protected final QueryContext context;

    public SqlQuery(QueryContext queryContext) {
        this.context = queryContext;
    }

    @Override
    public List<T> toList() {
        try {
            var query = toRawQuery();
            var queryResult = context.getQueryExecutor().executeQuery(query);
            var result = context.getQueryMapper().mapQueryResult(queryResult);
            return (List<T>)result;
        } catch (Exception e) {
            throw new RuntimeException("Unable to map query", e);
        }
    }

    @Override
    public Optional<T> toFirst()
    {
        var result = toList();
        if(result.isEmpty())
        {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    @Override
    public String toRawQuery() {
        return  context.getQueryModelTranslator().translateQueryModel(context.getQueryModel());
    }
}
