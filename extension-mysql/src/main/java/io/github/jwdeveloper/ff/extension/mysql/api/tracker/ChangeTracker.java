package io.github.jwdeveloper.ff.extension.mysql.api.tracker;

import io.github.jwdeveloper.ff.extension.mysql.api.DbEntry;

public interface ChangeTracker<T>
{
    DbEntry<T> insert(T entity);

    DbEntry<T> update(T entity);

    DbEntry<T> delete(T entity);

    void clear();
}
