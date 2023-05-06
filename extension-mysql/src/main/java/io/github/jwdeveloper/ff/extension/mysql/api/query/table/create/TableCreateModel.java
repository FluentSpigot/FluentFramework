package io.github.jwdeveloper.ff.extension.mysql.api.query.table.create;

import io.github.jwdeveloper.ff.extension.mysql.api.query.table.column.ColumnModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableCreateModel
{
    private String tableName;

    private List<ColumnModel> columnModels = new ArrayList<>();
}
