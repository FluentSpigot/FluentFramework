package io.github.jwdeveloper.ff.extension.mysql.implementation.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class SqlQueryExecutor {
    private Connection connection;

    public SqlQueryExecutor(Connection connection) {

    }


    public Connection getConnection() {
        return connection;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        var statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public boolean executeStatement(String query) throws SQLException {
        var statement = connection.createStatement();
        return statement.execute(query);
    }

    public int[] executeBatch(Collection<String> batches) throws SQLException {
        var statement = connection.createStatement();
        for (var batch : batches) {
            statement.addBatch(batch);
        }
        return statement.executeBatch();
    }


    public int executeInsert(String query) throws SQLException {
        final var statement = connection.createStatement();
        final var result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        final var resultSet = statement.getGeneratedKeys();
        if (!resultSet.next()) {
            throw new SQLException("Can not get new Key");
        }
        return resultSet.getInt(1);
    }

}
