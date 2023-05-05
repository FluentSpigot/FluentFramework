package io.github.jwdeveloper.ff.extension.mysql.api.query.delete;

import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereQuery;

public interface DeleteBridge<T> extends
        QueryBridge<T>,
        WhereQuery<WhereBridge<T>>
{

}
