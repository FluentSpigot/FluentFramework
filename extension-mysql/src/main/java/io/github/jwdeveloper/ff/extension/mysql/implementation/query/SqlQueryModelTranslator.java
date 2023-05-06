package io.github.jwdeveloper.ff.extension.mysql.implementation.query;

import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.delete.DeleteModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.create.TableCreateModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereModel;

//TODO better implementation
public class SqlQueryModelTranslator
{


    public SqlQueryModelTranslator()
    {

    }


    public String translateQueryModel(QueryModel queryModel) {
        var builder = new TextBuilder();

        handleCreateTable(builder,queryModel.getTableCreateModel());
        handleDelete(builder, queryModel.getDeleteModel());
        handleInsert(builder, queryModel.getInsertModel());
        handleUpdate(builder, queryModel.getUpdateModel());
        handleSelect(builder, queryModel.getSelectModel());
        handleWhere(builder, queryModel.getWhereModel(), SqlSyntaxUtils.WHERE);
        handleGroup(builder, queryModel.getGroupModel());
        handleOrder(builder, queryModel.getOrderModel());
        handleLimit(builder, queryModel.getLimitModel());

        builder.text(SqlSyntaxUtils.SEMI_COL);
        return builder.toString();
    }


    public void handleDelete(TextBuilder builder, DeleteModel model)
    {
        if(model == null)
        {
            return;
        }
        builder.text(SqlSyntaxUtils.DELETE_FROM, model.getFrom()).space();

    }
    public void handleUpdate(TextBuilder builder, UpdateModel model)
    {
        if(model == null)
        {
            return;
        }

        builder.text(SqlSyntaxUtils.UPDATE,model.getTable(), SqlSyntaxUtils.SET);
        var values = model.getValues();

        var i =0;
        for(var entry : values.entrySet())
        {
            builder.text(entry.getKey(),SqlSyntaxUtils.EQUALS, parseParam(entry.getValue()));
            if(i != values.size() -1)
            {
                builder.text(SqlSyntaxUtils.COMMA);
            }
            i++;
        }
    }

    public void handleInsert(TextBuilder builder, InsertModel model)
    {

        if(model == null)
        {
            return;
        }

        builder.text(SqlSyntaxUtils.INSERT_INTO, model.getTable()).space();
        builder.text(SqlSyntaxUtils.OPEN);
        var columns = model.getColumns();
        for(var i =0;i<columns.length;i++)
        {
            builder.text(columns[i]);
            if(i != columns.length -1)
            {
                builder.text(SqlSyntaxUtils.COMMA);
            }
        }

        builder.text(SqlSyntaxUtils.CLOSE);
        builder.space();
        builder.text(SqlSyntaxUtils.VALUES);
        builder.space();
        builder.text(SqlSyntaxUtils.OPEN);
        var values = model.getValues();
        for(var i =0;i<values.length;i++)
        {
            var parsed = parseParam(values[i]);
            builder.text(parsed);
            if(i != values.length -1)
            {
                builder.text(SqlSyntaxUtils.COMMA);
            }
        }

        builder.text(SqlSyntaxUtils.CLOSE);
    }

    public void handleSelect(TextBuilder builder, SelectModel model) {
        if (model == null) {
            return;
        }
        builder.text(SqlSyntaxUtils.SELECT).space();
        if (model.getColumns().isEmpty()) {
            builder.text(SqlSyntaxUtils.STAR).space();
        }


        var i = 0;
        for (var column : model.getColumns().entrySet()) {
            if (!column.getValue().equals(StringUtils.EMPTY)) {
                builder.text(column.getKey(), SqlSyntaxUtils.AS, column.getValue());
            } else {
                builder.text(column.getKey());
            }

            if (i < model.getColumns().size() - 1) {
                builder.text(SqlSyntaxUtils.COMMA).space();
            } else {
                builder.space();
            }
            i++;
        }


        builder.text(SqlSyntaxUtils.FROM).space();

        if (model.getFromTable() == null && model.getFromTableClass() == null) {
            throw new RuntimeException("Query From what table is not defined");
        }


        if (model.getFromTable() != null) {
            builder.text(model.getFromTable());
        }

        if (model.getFromTableClass() != null) {
            builder.text(model.getFromTableClass().getSimpleName());
        }



        builder.space();
    }


    public void handleWhere(TextBuilder builder, WhereModel model, String prefix) {
        if (model == null) {
            return;
        }
        builder.text(prefix).space();


        for (var instruction : model.getInstructions()) {
            var params = instruction.getParams();
            switch (instruction.getName()) {
                case "EQUAL":
                    builder.space().text(params[0],SqlSyntaxUtils.EQUALS, parseParam(params[1])).space();
                    continue;
                case "NOT_EQUAL":
                    builder.space().text(params[0], SqlSyntaxUtils.NOT_EQUALS, parseParam(params[1])).space();
                    continue;
                case "GREATER":
                    builder.space().text(params[0],SqlSyntaxUtils.GREATER, params[1]).space();
                    continue;
                case "LESS":
                    builder.space().text(params[0], SqlSyntaxUtils.LESSER, params[1]).space();
                    continue;
                case "IN":
                    builder.space().text(params[0], SqlSyntaxUtils.IN,SqlSyntaxUtils.OPEN, params[1], SqlSyntaxUtils.CLOSE).space();
                    continue;
                case "NOT_IN":
                    builder.space().text(params[0], SqlSyntaxUtils.NOT_IN,SqlSyntaxUtils.OPEN, params[1], SqlSyntaxUtils.CLOSE).space();
                    continue;
                case "BETWEEN":
                    builder.space().text(params[0], SqlSyntaxUtils.BETWEEN, parseParam(params[1]), SqlSyntaxUtils.AND, parseParam(params[2])).space();
                    continue;
                case "NOT_BETWEEN":
                    builder.space().text(params[0], SqlSyntaxUtils.NOT_BETWEEN, parseParam(params[1]), SqlSyntaxUtils.AND, parseParam(params[2])).space();
                    continue;
                case "LIKE":
                    builder.space().text(params[0], SqlSyntaxUtils.LIKE, parseParam(params[1])).space();
                    continue;
                case "NOT LIKE":
                    builder.space().text(params[0], SqlSyntaxUtils.NOT_LIKE, parseParam(params[1])).space();
                    continue;
                case "AND":
                    builder.space().text(SqlSyntaxUtils.AND).space();
                    continue;
                case "OR":
                    builder.space().text(SqlSyntaxUtils.OR).space();
                    continue;
                case "RAW_SQL":
                    builder.space().text(params[0]).space();
                    continue;
            }
        }
        builder.space();
    }

    public void handleCreateTable(TextBuilder builder, TableCreateModel model)
    {
        if(model == null)
        {
            return;
        }

        builder.text(SqlSyntaxUtils.CREATE_TABLE,model.getTableName());
        if(model.getColumnModels().isEmpty())
        {
            return;
        }


        builder.text(SqlSyntaxUtils.OPEN);
        var columns = model.getColumnModels();
        for (int i = 0; i < columns.size(); i++) {

            var column = columns.get(i);

            builder.text(column.getColumnName(),column.getColumnType());

            if(column.getColumnTypeSize() != -1)
            {
                builder.text("(",column.getColumnTypeSize(),")");
            }

            if(column.isAutoIncrement())
            {
                builder.text(SqlSyntaxUtils.AUTO_INCREMENT);
            }

            if(column.isRequired())
            {
                builder.text(SqlSyntaxUtils.NOT_NULL);
            }

            if(column.isPrimaryKey())
            {
                builder.text(SqlSyntaxUtils.PRIMARY_KEY);
            }


            if(column.isForeignKey())
            {
                builder.text(SqlSyntaxUtils.FOREIGN_KEY,column.getForeignTable(),"(",column.getForeignColumn(),")");
            }

            if (i != columns.size() - 1) {
                builder.text(SqlSyntaxUtils.COMMA);
            }
        }
        builder.text(SqlSyntaxUtils.CLOSE);
    }

    private String parseParam(Object param)
    {
        if(param instanceof String)
        {
            return "'"+param+"'";
        }
        return param.toString();
    }

    public void handleGroup(TextBuilder builder, GroupModel model) {
        if (model == null) {
            return;
        }
        builder.text(SqlSyntaxUtils.GROUP_BY);
        var i = 0;
        for (var column : model.getColumns()) {
            builder.text(column);
            if (i < model.getColumns().length - 1) {
                builder.text(SqlSyntaxUtils.COMMA).space();
            } else {
                builder.space();
            }
            i++;
        }

        builder.space();
        if (model.getHaving() == null) {
            return;
        }
        handleWhere(builder, model.getHaving(), SqlSyntaxUtils.HAVING);
    }

    public void handleOrder(TextBuilder builder, OrderModel model) {
        if (model == null) {
            return;
        }
        builder.text(SqlSyntaxUtils.ORDER_BY).space();

        var i = 0;
        for (var column : model.getColumns()) {
            builder.text(column);
            if (i < model.getColumns().length - 1) {
                builder.text(SqlSyntaxUtils.COMMA).space();
            } else {
                builder.space();
            }
            i++;
        }

        if (model.isAsc()) {
            builder.text(SqlSyntaxUtils.ASC);
        } else {
            builder.text(SqlSyntaxUtils.DESC);
        }
        builder.space();
    }

    public void handleLimit(TextBuilder builder, LimitModel model) {
        if (model == null) {
            return;
        }
        builder.text(SqlSyntaxUtils.LIMIT, model.getOffset(),SqlSyntaxUtils.COMMA, model.getCount());
    }
}
