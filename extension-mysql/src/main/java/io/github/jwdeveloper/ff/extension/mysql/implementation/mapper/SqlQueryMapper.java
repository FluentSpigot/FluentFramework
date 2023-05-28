package io.github.jwdeveloper.ff.extension.mysql.implementation.mapper;

import io.github.jwdeveloper.ff.extension.mysql.implementation.models.TableModel;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SqlQueryMapper {

    private final TableModel tableModel;

    public SqlQueryMapper(TableModel tableModel)
    {
        this.tableModel = tableModel;
    }

    public List<Object> mapQueryResult(ResultSet resultSet) throws Exception {
        var tables = getModelConfig(resultSet);
        return getMappedResult(tables, resultSet);
    }

    private List<SqlMappingTableDto> getModelConfig(ResultSet resultSet) throws SQLException {
        var result = new ArrayList<SqlMappingTableDto>();
        var sqlMappingTableDto = getTable(tableModel, resultSet.getMetaData(), 0);
        result.add(sqlMappingTableDto);
       /* var size = sqlMappingTableDto.getColumnCount();
        for (var ref : joinedColumns) {
            var table = SqlTableModelFactory.getTableModel(ref.getField().getType());
            var dto = getTable(table, metadata, size);
            dto.joinedColumn = ref;
            size += table.getColumnCount();
            output.add(dto);
        }*/
        return result;
    }


    public <T> List<T> getMappedResult(List<SqlMappingTableDto> columnsInfo, ResultSet resultSet) throws Exception {
        var result = new ArrayList<T>();

        while (resultSet.next()) {
            T mainObj = null;
            for (int i = 0; i < columnsInfo.size(); i++) {
                var currentTable = columnsInfo.get(i);
                if (i == 0) {
                    mainObj = (T) createInstance(resultSet, currentTable);
                    continue;
                }
                var reference = createInstance(resultSet, currentTable);
                currentTable.getJoinedColumn().getField().set(mainObj, reference);
            }
            result.add(mainObj);
        }
        return result;
    }


    public SqlMappingTableDto getTable(TableModel tableModel, ResultSetMetaData metaData, int start) throws SQLException {
        var currentTableColumns = tableModel.getColumnsCount();
        var resultColumnsCount = metaData.getColumnCount();
        var endIndex = Math.min(resultColumnsCount, currentTableColumns);

        var dto = new SqlMappingTableDto();
        dto.setTableModel(tableModel);
        dto.setObjectType(tableModel.getClazz());
        for (int i = 1 + start; i <= start + endIndex; i++) {
            var colName = metaData.getColumnLabel(i);
            var columnInfo = tableModel.getColumn(colName);
            if (columnInfo.isEmpty())
                continue;

            dto.getColumnModels().put(i, columnInfo.get());
        }
        return dto;
    }

    public Object createInstance(ResultSet result, SqlMappingTableDto createObj) throws InstantiationException, IllegalAccessException, SQLException {
        var instance = createObj.getObjectType().newInstance();
        for (final var entrySet : createObj.getColumnModels().entrySet())
        {
            var value = result.getObject(entrySet.getKey());
            var column = entrySet.getValue();

            if (value == null && !column.isRequired()) {
                column.getField().set(instance, null);
                continue;
            }

            var fieldType = column.getField().getType().getSimpleName();
            if (fieldType.equals("UUID")) {
                column.getField().set(instance, UUID.fromString(value.toString()));
                continue;
            }
            column.getField().set(instance, value);
        }
        return instance;
    }
}
