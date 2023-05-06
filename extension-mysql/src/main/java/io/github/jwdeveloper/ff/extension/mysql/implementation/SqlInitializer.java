package io.github.jwdeveloper.ff.extension.mysql.implementation;

import io.github.jwdeveloper.ff.extension.mysql.api.DbTable;
import io.github.jwdeveloper.ff.extension.mysql.implementation.executor.SqlQueryExecutor;
import io.github.jwdeveloper.ff.extension.mysql.implementation.factories.ConnectionFactory;
import io.github.jwdeveloper.ff.extension.mysql.implementation.factories.TableModelFactory;
import io.github.jwdeveloper.ff.extension.mysql.implementation.mapper.SqlQueryMapper;
import io.github.jwdeveloper.ff.extension.mysql.implementation.models.SqlConnectionModel;
import io.github.jwdeveloper.ff.extension.mysql.implementation.models.TableModel;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryFactory;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryModelTranslator;
import io.github.jwdeveloper.ff.extension.mysql.implementation.tracker.SqlChangeTracker;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;

public class SqlInitializer {
    private final ConnectionFactory connectionFactory;
    private final TableModelFactory tableModelFactory;

    public SqlInitializer() {
        connectionFactory = new ConnectionFactory();
        tableModelFactory = new TableModelFactory();
    }

    public Connection getConnection(SqlConnectionModel connectionModel) {
        var connection = connectionFactory.getConnection(connectionModel);
        if (connection.isEmpty()) {
            throw new RuntimeException("Connection not established");
        }
        return connection.get();
    }

    public void intialize(Connection connection, SqlDbContext dbContext) throws Exception {
        var queryExecutor = new SqlQueryExecutor(connection);
        var dbClass = dbContext.getClass();
        for (var field : dbClass.getDeclaredFields()) {
            if (!field.getType().isAssignableFrom(DbTable.class)) {
                continue;
            }
            var tableModel = getTableModel(field);
            var dbTable = getSqlTable(tableModel, queryExecutor);
            field.setAccessible(true);
            field.set(dbContext, dbTable);
            field.setAccessible(false);
            dbContext.addTable(dbTable);
        }

        for (var table : dbContext.tables) {
            table.createTable();
        }
    }

    private TableModel getTableModel(Field field) throws ClassNotFoundException {
        var genericType = (ParameterizedType) field.getGenericType();
        var tableType = genericType.getActualTypeArguments()[0];
        var tableClass = Class.forName(tableType.getTypeName());
        var tableModel = tableModelFactory.createTableModel(tableClass);
        return tableModel;
    }

    private SqlTable getSqlTable(TableModel tableModel, SqlQueryExecutor queryExecutor) {
        var changeTracker = new SqlChangeTracker<>(tableModel);
        var queryFactory = new SqlQueryFactory(
                queryExecutor,
                new SqlQueryMapper(tableModel),
                new SqlQueryModelTranslator());
        return new SqlTable(tableModel, changeTracker, queryFactory, queryExecutor);
    }
}
