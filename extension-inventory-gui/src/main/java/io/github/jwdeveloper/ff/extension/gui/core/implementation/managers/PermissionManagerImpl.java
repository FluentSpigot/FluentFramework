package io.github.jwdeveloper.ff.extension.gui.core.implementation.managers;

import io.github.jwdeveloper.ff.extension.gui.core.api.InventorySettings;
import io.github.jwdeveloper.ff.core.spigot.permissions.implementation.PermissionsUtility;
import org.bukkit.entity.Player;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.permissions.PermissionManager;

import java.util.List;

public class PermissionManagerImpl implements PermissionManager
{
    private final InventorySettings settings;

    public PermissionManagerImpl(InventorySettings settings)
    {
        this.settings = settings;
    }

    @Override
    public void addPermissions(String... permissions) {
         settings.getPermissions().addAll(List.of(permissions));
    }

    @Override
    public void setPermissions(String... permissions) {
        settings.setPermissions(List.of(permissions));
    }

    @Override
    public String[] getPermissions() {
      return settings.getPermissions().toArray(new String[0]);
    }

    @Override
    public boolean validatePlayer(Player player)
    {
        return validatePlayer(player, settings.getPermissions());
    }

    @Override
    public boolean validatePlayer(Player player, List<String> permissions) {
        return PermissionsUtility.hasOnePermission(player,permissions);
    }
}
