package io.github.jwdeveloper.ff.extension.gameobject.neww.api.core;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

public interface Transportation {
    Vector translation();

    Vector translation(double x, double y, double z);

    Vector position();

    Vector position(double x, double y, double z);

    Vector rotation();

    Vector rotation(double x, double y, double z);

    Vector scale();

    Vector scale(double x, double y, double z);

    Transportation merge(Transportation second);

    World world();

    void setWorld(World world);

    Location toLocation();

    Transformation toBukkitTransformation();

    Transportation clone();

}
