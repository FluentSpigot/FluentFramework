package io.github.jwdeveloper.ff.extension.mysql.implementation.query.select;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQuery;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.group.SqlGroupBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.limit.SqlLimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.order.SqlOrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.where.SqlWhereBridge;

import java.util.function.Consumer;

public class SqlSelectBridge<T> extends SqlQuery<T> implements SelectBridge<T>, SelectQuery<SelectBridge<T>>
{
    public SqlSelectBridge(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public SelectBridge<T> select(Consumer<SelectOptions> options) {
        var sqlOptions = new SqlSelectOptions("SELECT");
        options.accept(sqlOptions);
        var model = sqlOptions.build();
        context.getQueryModel().setSelectModel(model);
        return this;
    }

    @Override
    public WhereBridge<T> where(Consumer<WhereOptions> options) {
        return new SqlWhereBridge(context).where(options);
    }

    @Override
    public GroupBridge<T> groupBy(String... columns) {
        return new SqlGroupBridge(context).groupBy(columns);
    }

    @Override
    public GroupBridge<T> groupByHaving(String column, Consumer<WhereOptions> havingOptions) {
        return new SqlGroupBridge(context).groupByHaving(column, havingOptions);
    }

    @Override
    public OrderBridge<T> orderBy(String... columns) {
        return new SqlOrderBridge(context).orderBy(columns);
    }

    @Override
    public OrderBridge<T> orderByAsc(String... columns) {
        return new SqlOrderBridge(context).orderByAsc(columns);
    }

    @Override
    public OrderBridge<T> orderByDesc(String... columns) {
        return new SqlOrderBridge(context).orderByDesc(columns);
    }

    @Override
    public LimitBridge<T> limit(int offset, int count) {
        return new SqlLimitBridge(context).limit(offset,count);
    }

    @Override
    public LimitBridge<T> limit(int count)
    {
        return new SqlLimitBridge(context).limit(count);
    }
}
