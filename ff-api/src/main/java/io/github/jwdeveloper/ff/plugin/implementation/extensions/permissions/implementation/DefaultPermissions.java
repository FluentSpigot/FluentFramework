package io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation;

import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionModel;
import lombok.Getter;

@Getter
public class DefaultPermissions
{
    private final PermissionModel commands;
    private final PermissionModel gui;
    private final PermissionModel plugin;

    public DefaultPermissions()
    {
        plugin = createDefaultModel("plugin");
        commands = createDefaultModel("commands");
        gui = createDefaultModel("gui");
    }

    private PermissionModel createDefaultModel(String name)
    {
        var permissionModel = new PermissionModel();
        permissionModel.setName(name);
        permissionModel.setTitle(name);
        return permissionModel;
    }
}
