package io.github.jwdeveloper.ff.extension.mysql.implementation.query.where;

import io.github.jwdeveloper.ff.extension.mysql.api.query.functions.SqlFunction;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereInstruction;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereOptions;

public class SqlWhereOptions implements WhereOptions {

    private final WhereModel whereModel;

    public SqlWhereOptions() {
        whereModel = new WhereModel();
    }


    @Override
    public WhereOptions sqlFunction(SqlFunction function, String column) {
        whereModel.addInstruction(WhereInstruction.create("FUNCTION", function, column));
        return this;
    }

    @Override
    public WhereOptions isEqual(String column, Object value) {
        whereModel.addInstruction(WhereInstruction.create("EQUAL", column, value));
        return this;
    }

    @Override
    public WhereOptions isNotEqual(String column, Object value) {
        whereModel.addInstruction(WhereInstruction.create("NOT_EQUAL", column, value));
        return this;
    }

    @Override
    public WhereOptions isGreaterThen(String column, Number value) {
        whereModel.addInstruction(WhereInstruction.create("GREATER", column, value));
        return this;
    }

    @Override
    public WhereOptions isLessThen(String column, Number value) {
        whereModel.addInstruction(WhereInstruction.create("LESS", column, value));
        return this;
    }

    @Override
    public WhereOptions isIn(String column, String subQuery) {
        whereModel.addInstruction(WhereInstruction.create("IN", column, subQuery));
        return this;
    }

    @Override
    public WhereOptions isIn(String column, Object... values) {
        whereModel.addInstruction(WhereInstruction.create("IN_2", column, values));
        return this;
    }

    @Override
    public WhereOptions isNotIn(String column, String subQuery) {
        whereModel.addInstruction(WhereInstruction.create("NOT_IN", column, subQuery));
        return this;
    }

    @Override
    public WhereOptions isNotIn(String column, Object... values) {
        whereModel.addInstruction(WhereInstruction.create("NOT_IN_2", column, values));
        return this;
    }

    @Override
    public WhereOptions isBetween(String column, Object value1, Object value2) {
        whereModel.addInstruction(WhereInstruction.create("BETWEEN", column, value1, value2));
        return this;
    }

    @Override
    public WhereOptions isNotBetween(String column, Object value1, Object value2) {
        whereModel.addInstruction(WhereInstruction.create("NOT_BETWEEN", column, value1, value2));
        return this;
    }

    @Override
    public WhereOptions isLike(String column, Object value) {
        whereModel.addInstruction(WhereInstruction.create("LIKE", column, value));
        return this;
    }

    @Override
    public WhereOptions isNotLike(String column, Object value) {
        whereModel.addInstruction(WhereInstruction.create("NOT_LIKE", column, value));
        return this;
    }

    @Override
    public WhereOptions or() {
        whereModel.addInstruction(WhereInstruction.create("OR"));
        return this;
    }

    @Override
    public WhereOptions and() {
        whereModel.addInstruction(WhereInstruction.create("AND"));
        return this;
    }

    @Override
    public WhereOptions rawSql(String custom) {
        whereModel.addInstruction(WhereInstruction.create("RAW_SQL", custom));
        return this;
    }

    public WhereModel build() {
        return whereModel;
    }


}
