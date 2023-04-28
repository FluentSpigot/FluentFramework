package io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api;

import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation.DefaultPermissions;

public interface FluentPermissionBuilder
{
    public FluentPermissionBuilder registerPermission(PermissionModel model);

    public FluentPermissionBuilder setBasePermissionName(String name);

    public DefaultPermissions defaultPermissionSections();

    public String getBasePermissionName();

}
