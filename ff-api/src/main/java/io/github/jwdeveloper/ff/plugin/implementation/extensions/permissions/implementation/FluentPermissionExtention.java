package io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation;

import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermission;

public class FluentPermissionExtention implements FluentApiExtension {

    private FluentPermissionBuilderImpl permissionBuilder;

    public FluentPermissionExtention(FluentPermissionBuilderImpl builder) {
        this.permissionBuilder = builder;
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder api) {

        api.container().register(FluentPermission.class, LifeTime.SINGLETON, container -> permissionBuilder.build());
    }

}
