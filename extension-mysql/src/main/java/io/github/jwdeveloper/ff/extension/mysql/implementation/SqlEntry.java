package io.github.jwdeveloper.ff.extension.mysql.implementation;

import io.github.jwdeveloper.ff.extension.mysql.api.DbEntry;
import io.github.jwdeveloper.ff.extension.mysql.api.enums.EntryState;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class SqlEntry<T> implements DbEntry<T> {

    @Getter
    private final T entity;
    private final HashMap<String, Object> fieldValues;
    private final HashMap<String, Object> updatedFields;
    private final Field keyField;

    @Getter
    private final String keyColumnName;

    @Getter
    @Setter
    private EntryState entryState;

    @Getter
    private LocalDateTime changedAt;


    public SqlEntry(T entity, Field keyField, String keyColumnName, EntryState entryState) {
        this.entity = entity;
        this.keyField = keyField;
        this.keyColumnName = keyColumnName;
        this.entryState = entryState;
        fieldValues = new HashMap<>();
        updatedFields = new HashMap<>();
    }


    public void setEntryState(EntryState entryState) {
        changedAt = LocalDateTime.now();
        this.entryState = entryState;
    }



    public boolean hasFieldValueChanged(String field, Object value) {
        var oldValue = fieldValues.get(field);

        if (oldValue == null && value == null) {
            return false;
        }

        if (oldValue != null && value == null) {
            return true;
        }

        if (oldValue == null)
            return true;

        if (oldValue != value) {
            return true;
        }
        return false;
    }

    public void setUpdateField(String field, Object value) {
        updatedFields.put(field, value);
    }

    public void setField(String field, Object value) {
        fieldValues.put(field, value);
    }

    @SneakyThrows
    public void setKey(int key) {
        keyField.set(entity, key);
    }

    @SneakyThrows
    public int getKey()
    {
        return keyField.getInt(entity);
    }

    public void clear() {
        fieldValues.clear();
        updatedFields.clear();
    }

    public void clearFields() {
        fieldValues.clear();
    }

    public void clearUpdatedFields()
    {
        updatedFields.clear();
    }

    public Set<Map.Entry<String,Object>> getUpdateFields()
    {
        return updatedFields.entrySet();
    }
}
