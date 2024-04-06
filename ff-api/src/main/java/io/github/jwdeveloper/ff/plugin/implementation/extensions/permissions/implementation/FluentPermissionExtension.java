package io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermission;

public class FluentPermissionExtension implements FluentApiExtension {

    private FluentPermissionBuilderImpl permissionBuilder;

    public FluentPermissionExtension(FluentPermissionBuilderImpl builder) {
        this.permissionBuilder = builder;
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder api) {

        api.container().registerSingleton(FluentPermission.class, container -> permissionBuilder.build());
    }

}
