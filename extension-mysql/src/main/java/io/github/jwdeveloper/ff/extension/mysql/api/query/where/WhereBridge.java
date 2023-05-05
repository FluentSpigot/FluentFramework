package io.github.jwdeveloper.ff.extension.mysql.api.query.where;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderQuery;

public interface WhereBridge<T> extends
        QueryBridge<T>,
        GroupQuery<GroupBridge<T>>,
        OrderQuery<OrderBridge<T>>,
        LimitQuery<LimitBridge<T>>
{

}
