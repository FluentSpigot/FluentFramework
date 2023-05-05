package io.github.jwdeveloper.ff.extension.mysql.api.query.order;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitQuery;

public interface OrderBridge<T> extends
        QueryBridge<T>,
        LimitQuery<LimitBridge<T>> {
}
