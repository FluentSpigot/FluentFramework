package io.github.jwdeveloper.ff.extension.mysql.implementation.tracker;

import io.github.jwdeveloper.ff.extension.mysql.api.DbEntry;
import io.github.jwdeveloper.ff.extension.mysql.api.enums.EntryState;
import io.github.jwdeveloper.ff.extension.mysql.implementation.models.TableModel;
import io.github.jwdeveloper.ff.extension.mysql.api.tracker.ChangeTracker;
import io.github.jwdeveloper.ff.extension.mysql.implementation.SqlEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SqlChangeTracker<T> implements ChangeTracker<T> {
    private final Map<Integer, SqlEntry<T>> trackedEntries;
    private final TableModel tableModel;

    public SqlChangeTracker(TableModel tableModel) {
        trackedEntries = new LinkedHashMap<>();
        this.tableModel = tableModel;
    }

    public DbEntry<T> update(T entity) {

        try {
            var sqlEntity = setState(entity, EntryState.UPDATE);
            for (var column : tableModel.getColumnList()) {
                if (column.isForeignKey())
                    continue;

                var newValue = column.getFieldValue(entity);
                if (sqlEntity.hasFieldValueChanged(column.getName(), newValue)) {
                    sqlEntity.setUpdateField(column.getName(), newValue);
                }
            }
            return sqlEntity;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to update Entity",e);
        }
    }



    public DbEntry<T> insert(T entity)  {
        try {
            var sqlEntity = setState(entity, EntryState.INSERT);
            sqlEntity.clearFields();
            for (var column : tableModel.getColumnList()) {
                if (column.isForeignKey())
                    continue;
                var value = column.getFieldValue(entity);
                sqlEntity.setField(column.getName(), value);
            }
            return sqlEntity;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to insert Entity",e);
        }
    }

    public DbEntry<T> delete(T entity) {
        return setState(entity, EntryState.DELETE);
    }

    public void clear() {
        for (final var entry : trackedEntries.values())
        {
            if (entry.getEntryState().equals(EntryState.DELETE)) {
                trackedEntries.remove(getObjectId(entry.getEntity()));
                continue;
            }
            for (var set : entry.getUpdateFields()) {
                entry.setField(set.getKey(), set.getValue());
            }
            entry.clearUpdatedFields();
            entry.setEntryState(EntryState.NONE);
        }
    }

    public Collection<SqlEntry<T>> getTrackedEntries() {
        return trackedEntries.values();
    }

    private SqlEntry<T> setState(T entity, EntryState entityState) {

        var objectId = getObjectId(entity);
        if (trackedEntries.containsKey(objectId)) {
            var entry = trackedEntries.get(objectId);
            entry.setEntryState(entityState);
            return entry;
        }

        var keyColumnName = tableModel.getPrimaryKeyColumn().getName();
        var keyField = tableModel.getPrimaryKeyColumn().getField();
        var entry = new SqlEntry<T>(entity,keyField ,keyColumnName, entityState);
        trackedEntries.put(objectId, entry);
        return entry;
    }

    private int getObjectId(Object object)
    {
        return System.identityHashCode(object);
    }
}
