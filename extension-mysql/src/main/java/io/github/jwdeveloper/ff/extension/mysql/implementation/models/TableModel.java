package io.github.jwdeveloper.ff.extension.mysql.implementation.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class TableModel
{
    private String name;
    private final List<ColumnModel> columnList = new ArrayList<>();

    private Class<?> clazz;

    private ColumnModel primaryKeyColumn;

    public Optional<ColumnModel> getColumn(String name)
    {
        return columnList.stream().filter(c -> c.getName().equals(name)).findAny();
    }
    public int getColumnsCount()
    {
        return columnList.stream().filter(e -> !e.isForeignKey()).toList().size();
    }

    public int getForeignKeysCount()
    {
        return getForeignKeys().size();
    }
    public List<ColumnModel> getForeignKeys()
    {
        return columnList.stream().filter(ColumnModel::isForeignKey).toList();
    }
}
