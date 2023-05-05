package io.github.jwdeveloper.ff.extension.mysql.api.query.limit;

public interface LimitQuery<T>
{
    T limit(int offset, int count);

    T limit(int count);
}
