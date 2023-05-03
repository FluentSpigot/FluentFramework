package io.github.jwdeveloper.ff.extension.database.mysql.query_builder.group_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_builder.group_builder.GroupBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.order_builder.OrderBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.where_builders.WhereBuilder;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.QueryBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.SqlSyntaxUtils;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.order_builder.OrderBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.where_builders.WhereBuilderImpl;

public class GroupBuilderImpl extends QueryBuilderImpl implements GroupBuilder
{
    public GroupBuilderImpl(StringBuilder query) {
        super(query);
    }

    public GroupBuilder groupBy()
    {
        query.append(SqlSyntaxUtils.GROUP_BY);
        return this;
    }


    public GroupBuilder column(String table) {
        query.append(table);
        return this;
    }

    public WhereBuilder having() {
        return new WhereBuilderImpl(query).rawSql(SqlSyntaxUtils.HAVING);
    }

    public OrderBuilder orderBy() {
        return new OrderBuilderImpl(query).orderBy();
    }
}
