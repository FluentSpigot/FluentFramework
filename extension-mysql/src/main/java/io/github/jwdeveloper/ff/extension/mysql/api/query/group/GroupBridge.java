package io.github.jwdeveloper.ff.extension.mysql.api.query.group;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitQuery;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderQuery;

public interface GroupBridge<T> extends
        QueryBridge<T>,
        OrderQuery<OrderBridge<T>>,
        LimitQuery<LimitBridge<T>>
{

}
