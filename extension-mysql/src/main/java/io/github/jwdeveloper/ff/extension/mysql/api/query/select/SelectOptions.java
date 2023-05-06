package io.github.jwdeveloper.ff.extension.mysql.api.query.select;

public interface SelectOptions
{
    SelectOptions column(String column);

    SelectOptions column(String column, String alias);

    SelectOptions columns(String ... columns);

    SelectOptions from(String table);
    SelectOptions queryType(SelectQueryType queryType);
}
