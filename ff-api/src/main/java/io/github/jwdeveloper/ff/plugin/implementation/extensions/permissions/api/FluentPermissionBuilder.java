package io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api;

import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation.DefaultPermissions;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionModel;

import java.util.function.Consumer;

public interface FluentPermissionBuilder
{
     FluentPermissionBuilder registerPermission(PermissionModel model);

     void registerPermission(Consumer<PermissionModel> model);

     FluentPermissionBuilder setBasePermissionName(String name);

     DefaultPermissions defaultPermissions();

     String getBasePermissionName();

}
