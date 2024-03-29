package io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.BuilderConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.AccessType;

public interface PropertiesConfig extends BuilderConfig {
    PropertiesConfig setUsageMessage(String usageMessage);

    PropertiesConfig setPermissionMessage(String permissionMessage);

    PropertiesConfig setLabel(String label);

    PropertiesConfig setDebbug(boolean isDebbug);

    PropertiesConfig setShortDescription(String shortDescription);

    PropertiesConfig setDescription(String description);

    PropertiesConfig setName(String name);

    PropertiesConfig addPermissions(String... permissions);

    PropertiesConfig setAccess(AccessType accessType);

    PropertiesConfig setHideFromTabDisplay(boolean isHide);

    PropertiesConfig setHideFromDocumentation(boolean value);
}
