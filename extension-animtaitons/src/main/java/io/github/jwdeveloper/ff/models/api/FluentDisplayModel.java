package io.github.jwdeveloper.ff.models.api;

import io.github.jwdeveloper.ff.models.api.data.Bone;
import org.bukkit.Location;

import java.util.Map;

public interface FluentDisplayModel {
    String getName();

    boolean hasBone(String name);

    Bone getBone(String name);

    Map<String, Bone> getBones();
    FluentDisplayModelInstance spawn(Location location);
}
