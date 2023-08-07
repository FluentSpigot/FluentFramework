package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.phisic;

import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing.DebbugBoxComponent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class HitboxComponent extends GameComponent
{
    private final double[] result = new double[10];
    public Vector min;
    public Vector max;
    private DebbugBoxComponent debbugBoxComponent;

    @Override
    public void onInitialization(FluentApiSpigot api)
    {
        debbugBoxComponent = gameobject().addComponent(DebbugBoxComponent.class);
    }

    private boolean isCollider(Location location) {
        return isCollider(location, 0);
    }

    private boolean isCollider(Location location, float length) {
        return rayBoxIntersect(location.toVector(),
                location.getDirection(),
                min,
                max) > length;
    }

    private double rayBoxIntersect(Vector position, Vector direction, Vector vmin, Vector vmax) {
        result[1] = (vmin.getX() - position.getX()) / direction.getX();
        result[2] = (vmax.getX() - position.getX()) / direction.getX();
        result[3] = (vmin.getY() - position.getY()) / direction.getY();
        result[4] = (vmax.getY() - position.getY()) / direction.getY();
        result[5] = (vmin.getZ() - position.getZ()) / direction.getZ();
        result[6] = (vmax.getZ() - position.getZ()) / direction.getZ();
        result[7] = max(max(min(result[1], result[2]), min(result[3], result[4])), min(result[5], result[6]));
        result[8] = min(min(max(result[1], result[2]), max(result[3], result[4])), max(result[5], result[6]));
        result[9] = (result[8] < 0 || result[7] > result[8]) ? 0 : result[7];
        return result[9];
    }

    private double max(double a, double b) {
        return Math.max(a, b);
    }

    private double min(double a, double b) {
        return Math.min(a, b);
    }
}
