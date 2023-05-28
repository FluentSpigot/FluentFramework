package io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation;

import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionModel;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class FluentPermissionBuilderImpl implements FluentPermissionBuilder {

    private final List<PermissionModel> models;
    private final Plugin javaPlugin;
    private final DefaultPermissions defaultPermissionBuilder;
    private String basePermission;

    public FluentPermissionBuilderImpl(Plugin javaPlugin)
    {
        models = new ArrayList<>();
        this.javaPlugin = javaPlugin;
        defaultPermissionBuilder = new DefaultPermissions();
    }

    @Override
    public FluentPermissionBuilder registerPermission(PermissionModel model) {
        models.add(model);
        return this;
    }

    @Override
    public FluentPermissionBuilder setBasePermissionName(String name) {
        basePermission= name;
        return this;
    }

    @Override
    public String getBasePermissionName() {

        if(StringUtils.isNullOrEmpty(basePermission))
        {
            return javaPlugin.getName();
        }
        return basePermission;
    }

    public DefaultPermissions defaultPermissions()
    {
        return defaultPermissionBuilder;
    }


    public FluentPermissionImpl build()
    {
        var pluginPermission = defaultPermissionBuilder.getPlugin();
        pluginPermission.setName(getBasePermissionName());

        var commands = defaultPermissionBuilder.getCommands();
        var gui  = defaultPermissionBuilder.getGui();


        pluginPermission.registerChild(commands);
        pluginPermission.registerChild(gui);

        models.add(pluginPermission);
        models.add(commands);
        models.add(gui);
        return new FluentPermissionImpl(models, basePermission);
    }
}
