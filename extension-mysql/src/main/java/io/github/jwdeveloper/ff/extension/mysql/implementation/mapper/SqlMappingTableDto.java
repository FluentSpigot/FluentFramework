package io.github.jwdeveloper.ff.extension.mysql.implementation.mapper;

import io.github.jwdeveloper.ff.extension.mysql.implementation.models.ColumnModel;
import io.github.jwdeveloper.ff.extension.mysql.implementation.models.TableModel;
import lombok.Data;

import java.util.HashMap;

@Data
public class SqlMappingTableDto
{
    private ColumnModel joinedColumn;
    private Class<?> objectType;
    private TableModel tableModel;
    private HashMap<Integer, ColumnModel> columnModels = new HashMap<Integer, ColumnModel>();
}