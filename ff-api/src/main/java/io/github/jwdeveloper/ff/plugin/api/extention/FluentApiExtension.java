package io.github.jwdeveloper.ff.plugin.api.extention;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.config.migrations.ExtensionMigration;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface FluentApiExtension {

    void onConfiguration(FluentApiSpigotBuilder builder);

    default void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
    }

    default void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
    }

    default ExtentionPriority getPriority() {
        return ExtentionPriority.MEDIUM;
    }

    default String getVersion() {
        return StringUtils.EMPTY;
    }

    default String getName() {
        return getClass().getSimpleName();
    }
    default List<ExtensionMigration> getMigrations() { return Collections.EMPTY_LIST;};

    FluentApiExtension EMPTY = (builder) -> { };
}
