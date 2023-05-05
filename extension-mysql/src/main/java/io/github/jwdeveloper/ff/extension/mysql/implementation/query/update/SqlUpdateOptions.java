package io.github.jwdeveloper.ff.extension.mysql.implementation.query.update;

import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateOptions;

import java.util.Map;

public class SqlUpdateOptions implements UpdateOptions {

    private final UpdateModel updateModel;

    public SqlUpdateOptions()
    {
        updateModel = new UpdateModel();
    }

    @Override
    public UpdateOptions table(String table) {
        updateModel.setTable(table);
        return this;
    }

    @Override
    public UpdateOptions set(Map<String, Object> values) {
        updateModel.setValues(values);
        return this;
    }

    @Override
    public UpdateOptions set(String column, Object value) {
        updateModel.getValues().put(column, value);
        return this;
    }

    public UpdateModel build()
    {
        return updateModel;
    }
}
