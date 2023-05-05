package io.github.jwdeveloper.ff.extension.mysql.implementation.query.select;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectQueryType;

public class SqlSelectOptions implements SelectOptions {
    private final SelectModel selectModel;

    public SqlSelectOptions(String type) {
        selectModel = new SelectModel();
        selectModel.setType(type);
    }



    @Override
    public SelectOptions column(String column) {
        selectModel.getColumns().put(column, StringUtils.EMPTY);
        return this;
    }

    @Override
    public SelectOptions column(String column, String alias) {
        selectModel.getColumns().put(column, alias);
        return this;
    }

    @Override
    public SelectOptions columns(String... columns) {
        for (var column : columns) {
            column(column);
        }
        return this;
    }

    @Override
    public SelectOptions from(String table) {
        selectModel.setFromTable(table);
        return this;
    }

    @Override
    public SelectOptions from(Class<?> tableModel) {
        selectModel.setFromTableClass(tableModel);
        return this;
    }

    @Override
    public SelectOptions queryType(SelectQueryType queryType) {
        selectModel.setQueryType(queryType);
        return this;
    }

    public SelectModel build() {
        return selectModel;
    }
}
