package io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.config;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.PropertiesConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.AccessType;
import io.github.jwdeveloper.ff.core.spigot.commands.api.models.CommandModel;

import java.util.Arrays;

public class PropertiesConfigImpl implements PropertiesConfig {

    private final CommandModel model;

    public PropertiesConfigImpl(CommandModel model) {
        this.model = model;
    }


    public PropertiesConfig setHideFromTabDisplay(boolean isHide) {
        model.setHideFromTabDisplay(isHide);
        return this;
    }

    @Override
    public PropertiesConfig setHideFromDocumentation(boolean value) {
        model.setHideFromDocumentation(value);
        return this;
    }

    @Override
    public PropertiesConfig setUsageMessage(String usageMessage) {
        model.setUsageMessage(usageMessage);
        return this;
    }

    @Override
    public PropertiesConfig setPermissionMessage(String permissionMessage) {
        model.setPermissionMessage(permissionMessage);
        return this;
    }

    @Override
    public PropertiesConfig setLabel(String label) {
        model.setLabel(label);
        return this;
    }

    @Override
    public PropertiesConfig setDebbug(boolean isDebbug) {
        model.setDebug(isDebbug);
        return this;
    }

    @Override
    public PropertiesConfig setAccess(AccessType accessType) {
        model.getCommandAccesses().add(accessType);
        return this;
    }

    @Override
    public PropertiesConfig setShortDescription(String shortDescription) {
        model.setShortDescription(shortDescription);
        return this;
    }

    @Override
    public PropertiesConfig setDescription(String description) {
        model.setDescription(description);
        return this;
    }

    @Override
    public PropertiesConfig setName(String name) {
        model.setName(name);
        return this;
    }


    @Override
    public PropertiesConfig addPermissions(String... permissions) {
        model.getPermissions().addAll(Arrays.asList(permissions));
        return this;
    }
}
