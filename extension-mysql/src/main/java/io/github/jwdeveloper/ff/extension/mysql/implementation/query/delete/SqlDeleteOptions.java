package io.github.jwdeveloper.ff.extension.mysql.implementation.query.delete;

import io.github.jwdeveloper.ff.extension.mysql.api.query.delete.DeleteModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.delete.DeleteOptions;

public class SqlDeleteOptions implements DeleteOptions {

    private final DeleteModel deleteModel = new DeleteModel();

    @Override
    public DeleteOptions from(String table) {
        deleteModel.setFrom(table);
        return this;
    }

    public DeleteModel build()
    {
        return deleteModel;
    }
}
