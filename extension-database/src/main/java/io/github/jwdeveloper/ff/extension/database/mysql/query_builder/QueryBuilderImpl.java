package io.github.jwdeveloper.ff.extension.database.mysql.query_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_abstract.AbstractQuery;

public abstract class QueryBuilderImpl implements AbstractQuery
{
    protected final StringBuilder query;

    public QueryBuilderImpl(StringBuilder query)
   {
       this.query = query;
   }

    public String getQueryClosed()
    {
        return query.append(SqlSyntaxUtils.SEMI_COL).toString();
    }

    public String getQuery()
    {
        return query.toString();
    }
}
