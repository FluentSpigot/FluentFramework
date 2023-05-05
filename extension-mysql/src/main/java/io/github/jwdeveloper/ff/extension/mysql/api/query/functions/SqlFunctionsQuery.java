package io.github.jwdeveloper.ff.extension.mysql.api.query.functions;

public interface SqlFunctionsQuery<T>
{
    T sqlFunction(SqlFunction function, String column);
}
