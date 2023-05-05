package io.github.jwdeveloper.ff.extension.mysql.implementation.query.insert;

import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertOptions;

public class SqlInsertOptions implements InsertOptions {

    private final InsertModel insertModel;

    public SqlInsertOptions()
    {
        insertModel = new InsertModel();
    }

    @Override
    public InsertOptions into(String table) {
        insertModel.setTable(table);
        return this;
    }

    @Override
    public InsertOptions columns(String... columns) {
        insertModel.setColumns(columns);
        return this;
    }

    @Override
    public InsertOptions values(Object... values) {
        insertModel.setValues(values);
        return this;
    }

    public InsertModel build()
    {
        return insertModel;
    }
}
