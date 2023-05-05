package io.github.jwdeveloper.ff.extension.mysql.api.query.where;

import io.github.jwdeveloper.ff.extension.mysql.api.query.functions.SqlFunctionsQuery;

public interface WhereOptions extends SqlFunctionsQuery<WhereOptions>
{
    WhereOptions isEqual(String column, Object value);

    WhereOptions isNotEqual(String column, Object value);

    WhereOptions isGreaterThen(String column, Number value);

    WhereOptions isLessThen(String column, Number value);

    WhereOptions isIn(String column, String subQuery);

    WhereOptions isIn(String column, Object... values);

    WhereOptions isNotIn(String column, String subQuery);

    WhereOptions isNotIn(String column, Object... values);
    WhereOptions isBetween(String column, Object value1, Object value2);

    WhereOptions isNotBetween(String column, Object value1, Object value2);

    WhereOptions isLike(Object value);

    WhereOptions isNotLike(Object value);

    WhereOptions or();

    WhereOptions and();

    WhereOptions rawSql(String custom);
}
