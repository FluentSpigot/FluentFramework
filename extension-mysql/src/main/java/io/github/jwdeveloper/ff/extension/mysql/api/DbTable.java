package io.github.jwdeveloper.ff.extension.mysql.api;

import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public interface DbTable<T>
{
    SelectBridge<T> select(Consumer<SelectOptions> options);

    SelectBridge<T> select();

    DbEntry<T> update(T entity);

    DbEntry<T> insert(T entity);

    DbEntry<T> delete(T entity);

    void saveChanges() throws SQLException;
}
