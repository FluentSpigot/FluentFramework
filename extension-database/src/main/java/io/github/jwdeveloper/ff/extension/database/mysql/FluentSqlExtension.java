package io.github.jwdeveloper.ff.extension.database.mysql;


import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.database.mysql.factories.SqlConnectionFactory;
import io.github.jwdeveloper.ff.extension.database.mysql.models.SqlConnection;
import io.github.jwdeveloper.ff.extension.database.mysql.models.SqlDbContext;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.database.mysql.models.SqlTable;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public class FluentSqlExtension implements FluentApiExtension {
    private final Consumer<SqlConnection> consumerConnection;

    private Class<?> contextClass;
    private Connection connection;


    public FluentSqlExtension(Consumer<SqlConnection> consumerConnection) {
        this.consumerConnection = consumerConnection;
    }


    @Override
    public ExtentionPriority getPriority() {
        return ExtentionPriority.LOW;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        FluentLogger.LOGGER.info("Lanching SQL extension");
        var contextClasses = builder.jarScanner().findBySuperClass(SqlDbContext.class);
        if(contextClasses.isEmpty())
        {
            throw new RuntimeException("Context class not found");
        }
        var contextClassOptional = contextClasses.stream().findFirst();
        contextClass = contextClassOptional.get();
        builder.container().register(contextClass, LifeTime.SINGLETON);
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var context = (SqlDbContext) fluentAPI.container().findInjection(contextClass);
        setTables(context);


        var connectionDto = new SqlConnection();
        consumerConnection.accept(connectionDto);
        var conn = new SqlConnectionFactory().getConnection(connectionDto);
        if (conn.isEmpty()) {
            throw new Exception("Can not establish connection");
        }
        connection = conn.get();
        context.setConnection(connection);
        for (var table : context.tables) {
            var sqlTable = (SqlTable) table;
            sqlTable.createTable();
        }
    }


    private void setTables(SqlDbContext context) throws IllegalAccessException {

        for (var field : contextClass.getDeclaredFields()) {
            field.setAccessible(true);
            var value = field.get(context);
            if (value != null)
                continue;
            var type = field.getType();
            if (!type.isInterface()) {
                continue;
            }

            var genericType = (ParameterizedType)field.getGenericType();
            var genericTypeArg = genericType.getActualTypeArguments()[0];
            var obj = new SqlTable((Class)genericTypeArg);
            context.tables.add(obj);
            field.set(context,obj);

        }
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws SQLException {
        if (connection == null) {
            return;
        }
        connection.close();
    }
}
