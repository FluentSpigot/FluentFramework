package io.github.jwdeveloper.ff.core.spigot.permissions.api;

import java.util.List;

public record PermissionDto(Class<?> _class, List<PermissionModel> permissionModels)
{

}
