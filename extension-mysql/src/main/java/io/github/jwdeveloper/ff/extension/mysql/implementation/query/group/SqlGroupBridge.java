package io.github.jwdeveloper.ff.extension.mysql.implementation.query.group;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.limit.SqlLimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.order.SqlOrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.where.SqlWhereOptions;

import java.util.function.Consumer;

public class SqlGroupBridge<T> extends SqlQuery<T> implements GroupBridge<T>, GroupQuery<GroupBridge<T>> {


    public SqlGroupBridge(QueryContext queryModel) {
        super(queryModel);
    }

    @Override
    public LimitBridge limit(int offset, int count) {
        return new SqlLimitBridge(context).limit(offset, count);
    }

    @Override
    public LimitBridge limit(int count) {
        return new SqlLimitBridge(context).limit(count);
    }

    @Override
    public OrderBridge orderBy(String... columns) {
        return new SqlOrderBridge(context).orderBy(columns);
    }

    @Override
    public OrderBridge orderByAsc(String... columns) {
        return new SqlOrderBridge(context).orderBy(columns);
    }

    @Override
    public OrderBridge orderByDesc(String... columns) {
        return new SqlOrderBridge(context).orderBy(columns);
    }


    @Override
    public GroupBridge groupBy(String... columns) {
        context.getQueryModel().setGroupModel(new GroupModel(columns));
        return this;
    }

    @Override
    public GroupBridge groupByHaving(String column, Consumer<WhereOptions> havingOptions)
    {
        var sqlHavingOptions = new SqlWhereOptions();
        havingOptions.accept(sqlHavingOptions);
        var havingModel = sqlHavingOptions.build();
        context.getQueryModel().setGroupModel(new GroupModel(havingModel, column));
        return this;
    }
}
