package io.github.jwdeveloper.ff.extension.mysql.implementation.models;

import io.github.jwdeveloper.ff.extension.mysql.api.DbEntry;
import io.github.jwdeveloper.ff.extension.mysql.api.DbTable;
import io.github.jwdeveloper.ff.extension.mysql.api.models.TableModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.executor.SqlQueryExecutor;
import io.github.jwdeveloper.ff.extension.mysql.implementation.mapper.SqlQueryMapper;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryFactory;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryModelTranslator;
import io.github.jwdeveloper.ff.extension.mysql.implementation.tracker.SqlChangeTracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

public class SqlTable<T> implements DbTable<T> {
    private final SqlQueryExecutor queryExecutor;
    private final SqlChangeTracker<T> changeTracker;

    private final SqlQueryFactory queryFactory;
    private final TableModel tableModel;

    public SqlTable(TableModel tableModel,
                    SqlChangeTracker<T> sqlChangeTracker,
                    SqlQueryFactory queryFactory,
                    SqlQueryExecutor queryExecutor) {
        this.tableModel = tableModel;
        this.changeTracker = sqlChangeTracker;
        this.queryExecutor = queryExecutor;
        this.queryFactory = queryFactory;
    }

    public SelectBridge<T> select(Consumer<SelectOptions> options) {
        return queryFactory.select(getQueryContext(), x ->
        {
            x.from(tableModel.getName());
            options.accept(x);
        });
    }

    @Override
    public SelectBridge<T> select() {
        return select((x) -> {
            x.from(tableModel.getName());
        });
    }

    public DbEntry<T> update(T entity) {
        return changeTracker.update(entity);
    }

    public DbEntry<T> insert(T entity) {
        return changeTracker.insert(entity);
    }

    public DbEntry<T> delete(T entity) {
        return changeTracker.delete(entity);
    }


    public void createTable() {
        try {
            var createTableQuery = queryFactory.createTable(tableModel);
            queryExecutor.executeQuery(createTableQuery);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create table", e);
        }
    }


    public void dropTable() {
        try {
            var batches = new String[3];
            batches[0] = "SET FOREIGN_KEY_CHECKS = 0;";
            batches[1] = queryFactory.dropTable(tableModel);
            batches[2] = "SET FOREIGN_KEY_CHECKS = 1;";
            queryExecutor.executeBatch(Arrays.stream(batches).toList());
        } catch (Exception e) {
            throw new RuntimeException("Unable to drop table", e);
        }
    }

    public void saveChanges() throws SQLException {

        var queriesDto = getEntryQueryDto();
        var connection = queryExecutor.getConnection();
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        executeInsertQuery(queriesDto.insertQueries);
        queryExecutor.executeBatch(queriesDto.updateQuery.values());
        queryExecutor.executeBatch(queriesDto.deleteQuery.values());
        connection.commit();

        changeTracker.clear();
    }

    private EntryQueryDto getEntryQueryDto() {
        final var entries = changeTracker.getTrackedEntries();
        final var dto = new EntryQueryDto();
        for (final var entry : entries) {
            switch (entry.getAction()) {
                case INSERT -> dto.insertQueries.put(entry, insertQuery(entry));
                case UPDATE -> dto.updateQuery.put(entry, updateQuery(entry));
                case DELETE -> dto.deleteQuery.put(entry, deleteQuery(entry));
            }
        }
        return dto;
    }


    private String insertQuery(SqlEntry<T> entry) {
        return queryFactory
                .delete(getQueryContext(), selectOptions ->
                {
                    selectOptions.from(tableModel.getName());
                })
                .where(whereOptions ->
                {
                    whereOptions.isEqual(entry.getKeyColumnName(), entry.getKey());
                })
                .toRawQuery();
    }

    private String updateQuery(SqlEntry<T> entry) {
        return queryFactory
                .delete(getQueryContext(), selectOptions ->
                {
                    selectOptions.from(tableModel.getName());
                })
                .where(whereOptions ->
                {
                    whereOptions.isEqual(entry.getKeyColumnName(), entry.getKey());
                })
                .toRawQuery();
    }

    private String deleteQuery(SqlEntry<T> entry) {
        return queryFactory
                .delete(getQueryContext(), selectOptions ->
                {
                    selectOptions.from(tableModel.getName());
                })
                .where(whereOptions ->
                {
                    whereOptions.isEqual(entry.getKeyColumnName(), entry.getKey());
                })
                .toRawQuery();
    }

    private QueryContext getQueryContext() {
        return new QueryContext(new QueryModel(),
                queryExecutor,
                new SqlQueryMapper(tableModel),
                new SqlQueryModelTranslator());
    }

    private void executeInsertQuery(HashMap<SqlEntry<T>, String> queries) throws SQLException {

        for (var entrySet : queries.entrySet()) {
            var query = entrySet.getValue();
            var entity = entrySet.getKey();
            var key = queryExecutor.executeInsert(query);
            entity.setKey(key);
        }
    }

    private class EntryQueryDto {
        HashMap<SqlEntry<T>, String> insertQueries = new HashMap();
        HashMap<SqlEntry<T>, String> updateQuery = new HashMap();
        HashMap<SqlEntry<T>, String> deleteQuery = new HashMap();
    }
}
