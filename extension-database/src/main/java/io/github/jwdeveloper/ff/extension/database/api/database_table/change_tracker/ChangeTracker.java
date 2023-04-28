package io.github.jwdeveloper.ff.extension.database.api.database_table.change_tracker;
import io.github.jwdeveloper.ff.extension.database.api.database_table.DbEntry;


public interface ChangeTracker<T>
{
     DbEntry<T> insert(T entity);

     DbEntry<T> update(T entity);

     DbEntry<T> delete(T entity);

     void clear();
}
