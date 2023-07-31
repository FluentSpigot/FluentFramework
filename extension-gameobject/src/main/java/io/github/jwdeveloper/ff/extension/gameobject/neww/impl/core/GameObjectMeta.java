package io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core;

import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.GameObjectMetadata;
import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

@Data
public class GameObjectMeta implements GameObjectMetadata
{
    private UUID uuid = UUID.randomUUID();

    private String tag;

    private String name;

    private Location location;

    private boolean active = true;

    private boolean visible = true;

    @Override
    public boolean hasTag(String tag) {
        return false;
    }

}
