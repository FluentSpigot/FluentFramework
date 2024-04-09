package io.github.jwdeveloper.ff.extension.database.mysql.factories;

import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.dependance.injector.api.enums.RegistrationType;
import io.github.jwdeveloper.dependance.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.dependance.injector.implementation.containers.DefaultContainer;
import io.github.jwdeveloper.ff.extension.database.mysql.models.SqlDbContext;


import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjectionImpl;
import lombok.SneakyThrows;

public class SqlDbContextFactory {
    @SneakyThrows
    public static <T extends SqlDbContext> T getDbContext(Class<T> contextType) {
        var registrationInfo = new RegistrationInfo(
                null,
                contextType,
                null,
                LifeTime.SINGLETON,
                RegistrationType.OnlyImpl);
        var injection = FluentApi.container();
        var container = (DefaultContainer) injection;
        container.register(registrationInfo);
        var context = (SqlDbContext) injection.findInjection(contextType);

        return (T) context;
    }
}
