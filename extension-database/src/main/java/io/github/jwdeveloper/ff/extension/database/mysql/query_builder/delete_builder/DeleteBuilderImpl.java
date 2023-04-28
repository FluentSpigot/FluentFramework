package io.github.jwdeveloper.ff.extension.database.mysql.query_builder.delete_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_builder.delete_builder.DeleteBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.where_builders.WhereBuilderBridge;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.SqlSyntaxUtils;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.bridge_builder.BridgeBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.QueryBuilderImpl;

public class DeleteBuilderImpl extends QueryBuilderImpl implements DeleteBuilder {

    public DeleteBuilderImpl() {
       this(new StringBuilder());
    }

    public DeleteBuilderImpl(StringBuilder query) {
        super(query);
        query.append(SqlSyntaxUtils.DELETE_FROM);
    }

    public WhereBuilderBridge from(Class<?> tableClass)
    {
        return from(tableClass.getSimpleName());
    }
    public WhereBuilderBridge from(String table)
    {
        query.append(table);
        return new BridgeBuilderImpl(query);
    }
}
