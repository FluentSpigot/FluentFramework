package io.github.jwdeveloper.ff.core.validator.implementation.player;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.validator.api.ValidationRule;
import org.bukkit.entity.Player;

import java.util.List;

public class PermissionRule implements ValidationRule<Player> {

    private final List<String> permissions;


    public PermissionRule(List<String> permissions)
    {
        this.permissions = permissions;
    }


    @Override
    public ActionResult<Player> validate(Player player) {
        if (player.isOp()) {
            return ActionResult.success(player);
        }
        if (permissions == null || permissions.size() == 0) {
            return ActionResult.success(player);
        }
        var last = StringUtils.EMPTY;
        var current = StringUtils.EMPTY;
        var subPermissions = new String[0];
        for (var permission : permissions) {
            if (permission == null) {
                continue;
            }
            subPermissions = permission.split("\\.");
            last = StringUtils.EMPTY;
            current = StringUtils.EMPTY;
            for (var i = 0; i < subPermissions.length; i++) {
                if (last.equals(StringUtils.EMPTY))
                    current = subPermissions[i].replace(".", StringUtils.EMPTY);
                else
                    current = last+ "."+ subPermissions[i];
                last = current;

                if (player.hasPermission(current) || player.hasPermission(current+".*")) {
                    return ActionResult.success(player);
                }
            }
        }
        return ActionResult.failed(player,"Player has insefficent permissons");
    }
}
