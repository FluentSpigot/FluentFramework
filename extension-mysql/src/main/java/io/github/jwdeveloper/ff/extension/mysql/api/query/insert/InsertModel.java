package io.github.jwdeveloper.ff.extension.mysql.api.query.insert;

import lombok.Data;

@Data
public class InsertModel
{
    private String table;

    private String[] columns;

    private Object[] values;
}
