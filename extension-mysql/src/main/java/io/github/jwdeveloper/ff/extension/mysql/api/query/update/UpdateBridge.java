package io.github.jwdeveloper.ff.extension.mysql.api.query.update;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereQuery;

public interface UpdateBridge<T> extends
        QueryBridge<T>,
        WhereQuery<WhereBridge<T>>
{

}
