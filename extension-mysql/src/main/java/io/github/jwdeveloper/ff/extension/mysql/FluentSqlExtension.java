package io.github.jwdeveloper.ff.extension.mysql;

import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.mysql.implementation.DbContext;
import io.github.jwdeveloper.ff.extension.mysql.implementation.SqlInitializer;
import io.github.jwdeveloper.ff.extension.mysql.implementation.models.SqlConnectionModel;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPiority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public class FluentSqlExtension implements FluentApiExtension {
    private final Consumer<SqlConnectionModel> consumerConnection;

    private Class<?> contextClass;
    private Connection connection;

    public FluentSqlExtension(Class<?> contextClass, Consumer<SqlConnectionModel> consumerConnection) {
        this.consumerConnection = consumerConnection;
        this.contextClass = contextClass;
    }


    @Override
    public ExtentionPiority getPiority() {
        return ExtentionPiority.LOW;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        if (contextClass != null) {
            builder.container().register(contextClass, LifeTime.SINGLETON);
            return;
        }

        var contextClassOptional = builder
                .classFinder()
                .findBySuperClass(DbContext.class)
                .stream()
                .findFirst();
        if (contextClassOptional.isEmpty()) {
            throw new RuntimeException("SqlDbContext class not found");
        }
        contextClass = contextClassOptional.get();
        builder.container().register(contextClass, LifeTime.SINGLETON);
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var context = (DbContext) fluentAPI.container().findInjection(contextClass);
        var initializer = new SqlInitializer();
        var connectionDto = new SqlConnectionModel();
        consumerConnection.accept(connectionDto);
        connection = initializer.getConnection(connectionDto);
        try
        {
            initializer.intialize(connection, context);
        } catch (Exception e)
        {
            connection.close();
            throw new RuntimeException("Unable initialize SQL", e);
        }
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws SQLException {
        if(connection == null)
        {
            return;
        }
        connection.close();
    }
}
