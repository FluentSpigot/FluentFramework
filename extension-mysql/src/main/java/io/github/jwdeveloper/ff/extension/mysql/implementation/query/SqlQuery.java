package io.github.jwdeveloper.ff.extension.mysql.implementation.query;

import io.github.jwdeveloper.ff.core.spigot.tasks.FluentTask;
import io.github.jwdeveloper.ff.extension.mysql.api.query.AbstractQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitModel;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
    public void toListAsync(Consumer<List<T>> onDone) {
        new FluentTask().taskAsync(() ->
        {
            var result = toList();
            onDone.accept(result);
        });
    }

    @Override
    public Optional<T> toFirst()
    {
        context.getQueryModel().setLimitModel(new LimitModel(0,1));
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
