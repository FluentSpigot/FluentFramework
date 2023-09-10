package io.github.jwdeveloper.ff.extension.mysql.implementation.factories;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.mysql.implementation.models.SqlConnectionModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class ConnectionFactory
{
    public Optional<Connection> getConnection(SqlConnectionModel connectionModel)
    {
        try {
            final var url = getConnectionString(connectionModel);
            var connection = DriverManager.getConnection(url, connectionModel.getUser(), connectionModel.getPassword());
            if (connection == null || connection.isClosed()) {
                return Optional.empty();
            }
            return Optional.of(connection);
        } catch (SQLException e) {
            FluentLogger.LOGGER.error("Connecting to SQL error", e);
            return Optional.empty();
        }
    }

    public String getConnectionString(SqlConnectionModel model) {
        return new StringBuilder()
                .append("jdbc:mysql://")
                .append(model.getServer())
                .append("/")
                .append(model.getDatabase())
                .append("?")
                .append("autoReconnect=")
                .append(model.isAutoReconnect())
                .append("&")
                .append("useSSL=")
                .append(model.isUseSll())
                .toString();
    }
}
