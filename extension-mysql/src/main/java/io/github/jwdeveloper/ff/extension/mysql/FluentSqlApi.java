package io.github.jwdeveloper.ff.extension.mysql;

import io.github.jwdeveloper.ff.extension.mysql.implementation.DbContext;
import io.github.jwdeveloper.ff.extension.mysql.implementation.models.SqlConnectionModel;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentSqlApi {
    public static FluentApiExtension useMySql(Consumer<SqlConnectionModel> connectionConsumer) {
        return new FluentSqlExtension(null, connectionConsumer);
    }

    public static FluentApiExtension useMySql(Class<? extends DbContext> dbContextClass, Consumer<SqlConnectionModel> connectionConsumer) {
        return new FluentSqlExtension(dbContextClass, connectionConsumer);
    }
}
