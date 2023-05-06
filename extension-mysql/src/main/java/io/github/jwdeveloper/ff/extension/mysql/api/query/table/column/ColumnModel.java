package io.github.jwdeveloper.ff.extension.mysql.api.query.table.column;

import lombok.Data;

@Data
public class ColumnModel {

    private String columnName;

    private boolean isPrimaryKey;

    private boolean isAutoIncrement;

    private boolean isRequired;

    private boolean isForeignKey;

    private String foreignTable;

    private String foreignColumn;

    private String columnType;

    private int columnTypeSize = -1;
}
