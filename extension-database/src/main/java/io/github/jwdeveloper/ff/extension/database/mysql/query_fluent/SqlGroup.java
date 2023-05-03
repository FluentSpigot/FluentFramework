package io.github.jwdeveloper.ff.extension.database.mysql.query_fluent;

import io.github.jwdeveloper.ff.extension.database.api.database_table.models.TableModel;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.group.GroupFluent;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.order.OrderFluent;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.where.WhereFluent;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.SqlSyntaxUtils;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.group_builder.GroupBuilderImpl;

import java.sql.Connection;

public class SqlGroup<T> extends SqlQuery<T> implements GroupFluent<T> {

    private final GroupBuilderImpl groupBuilder;

    public SqlGroup(StringBuilder query, Connection connection, TableModel tableModel) {
        super(query, connection, tableModel);
        groupBuilder = new GroupBuilderImpl(query);
        groupBuilder.groupBy();
    }

    @Override
    public String getQueryClosed() {
        return groupBuilder.getQuery().concat(SqlSyntaxUtils.SEMI_COL);
    }

    @Override
    public String getQuery() {
        return groupBuilder.getQuery();
    }

    @Override
    public GroupFluent<T> column(String columns) {
        groupBuilder.column(columns);
        return this;
    }

    @Override
    public WhereFluent<T> having() {
        getQuery();
        return new SqlWhere<T>(query, connection, tableModel);
    }

    @Override
    public OrderFluent<T> orderBy() {
        getQuery();
        return new SqlOrder<T>(query, connection, tableModel);
    }
}
