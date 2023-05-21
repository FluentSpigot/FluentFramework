package io.github.jwdeveloper.ff.plugin.api.extention;

import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;

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
}
