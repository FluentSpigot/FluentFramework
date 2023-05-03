package io.github.jwdeveloper.ff.extension.database.mysql.query_fluent;


import com.google.common.base.Stopwatch;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.extension.database.api.database_table.models.ColumnModel;
import io.github.jwdeveloper.ff.extension.database.api.database_table.models.TableModel;
import io.github.jwdeveloper.ff.extension.database.api.query_fluent.QueryFluent;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.SqlSyntaxUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class SqlQuery<T> implements QueryFluent<T> {
    protected StringBuilder query;
    protected Connection connection;
    protected TableModel tableModel;
    protected Set<ColumnModel> joinedColumns;
    protected Stopwatch stopper;

    public SqlQuery(StringBuilder query, Connection connection, TableModel tableModel) {
        this.connection = connection;
        this.tableModel = tableModel;
        this.query = query;
        joinedColumns = new HashSet<>();
        stopper = Stopwatch.createStarted();
    }

    public SqlQuery(Connection connection, TableModel tableModel) {
        this(new StringBuilder(), connection, tableModel);
    }

    public List<T> toList() {
        var result = executeQuery(getQueryClosed());
        return getResult(result, tableModel);
    }

    public List<T> toList(int amount) {
        var query = getQuery()
                .concat(" LIMIT ")
                .concat(String.valueOf(amount))
                .concat(SqlSyntaxUtils.SEMI_COL);
        var result = executeQuery(query);
        return getResult(result, tableModel);
    }

    public Optional<T> first() {
        var result = toList(1);
        return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));
    }

    private List<T> getResult(ResultSet resultSet, TableModel tableModel) {
        // stopper.start();
        var result = new SqlQueryExecutor<T>(resultSet)
                .setTable(tableModel)
                .setJoins(joinedColumns)
                .toList();
        // stopper.stop();
        return result;
    }


    private ResultSet executeQuery(String query) {

        try {
            var statement = connection.prepareStatement(query);

            //   stopper.start();
            var result = statement.executeQuery();
            //stopper.stop(query);
            //  stopper.stop();
            return result;
        } catch (Exception e) {

            FluentLogger.LOGGER.error("Error while executing query: "+query);
            throw new RuntimeException(e);
        }
    }

}
