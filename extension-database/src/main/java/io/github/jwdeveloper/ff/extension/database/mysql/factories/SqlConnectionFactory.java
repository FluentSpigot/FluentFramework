package io.github.jwdeveloper.ff.extension.database.mysql.factories;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.database.api.conncetion.DbConnectionFactory;
import io.github.jwdeveloper.ff.extension.database.mysql.models.SqlConnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class SqlConnectionFactory implements DbConnectionFactory<Connection, SqlConnection> {

    public Optional<Connection> getConnection(SqlConnection connectionDto) {
        try {
            final var url = connectionDto.getConnectionString();
            var connection = DriverManager.getConnection(url, connectionDto.getUser(), connectionDto.getPassword());
            if (connection == null || connection.isClosed()) {
                return Optional.empty();
            }
            return Optional.of(connection);
        } catch (SQLException e) {
            FluentLogger.LOGGER.error("Connecting to SQL error", e);
            return Optional.empty();
        }
    }
}
