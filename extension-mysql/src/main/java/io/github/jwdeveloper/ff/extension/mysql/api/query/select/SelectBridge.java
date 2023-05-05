package io.github.jwdeveloper.ff.extension.mysql.api.query.select;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereQuery;

public interface SelectBridge<T> extends
        QueryBridge<T>,
        WhereQuery<WhereBridge<T>>,
        GroupQuery<GroupBridge<T>>,
        OrderQuery<OrderBridge<T>>,
        LimitQuery<LimitBridge<T>>
{

}
