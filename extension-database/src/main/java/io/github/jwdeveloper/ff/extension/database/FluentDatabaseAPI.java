package io.github.jwdeveloper.ff.extension.database;

import io.github.jwdeveloper.ff.extension.database.mysql.FluentSqlExtension;
import io.github.jwdeveloper.ff.extension.database.mysql.models.SqlConnection;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentDatabaseAPI
{
    public static FluentApiExtension useMySql(Consumer<SqlConnection> connectionConsumer)
    {
      return new FluentSqlExtension(connectionConsumer);
    }
}
