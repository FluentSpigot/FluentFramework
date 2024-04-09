package io.github.jwdeveloper.ff.core.common.java;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.World;

import java.text.DecimalFormat;
import java.util.List;

public class MathUtility {
    public static Location lerp(Location location, Location target, double t) {
        if (!location.getWorld().equals(target.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world");
        }

        double x = lerp((float) location.getX(), (float) target.getX(), (float) t);
        double y = lerp((float) location.getY(), (float) target.getY(), (float) t);
        double z = lerp((float) location.getZ(), (float) target.getZ(), (float) t);
        float yaw = lerpAngle(location.getYaw(), target.getYaw(), (float) t);
        float pitch = lerpAngle(location.getPitch(), target.getPitch(), (float) t);

        return new Location(location.getWorld(), x, y, z, yaw, pitch);
    }

    public static float lerpAngle(float start, float end, float t) {
        float diff = Math.abs(end - start);
        if (diff > 180) {
            // We need to add on to one of the values.
            if (end > start) {
                // We'll add it on to start...
                start += 360;
            } else {
                // Add it on to end.
                end += 360;
            }
        }

        float value = (start + ((end - start) * t));

        // Make sure we turn it back when we have the shortest route...
        if (value >= 360) {
            value -= 360;
        }

        return value;
    }

    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    public static double getPersent(double max, double current) {
        if (current > max)
            current = max;
        if (current <= 0)
            current = 1;
        return current / max;
    }

    public static int getRandom(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static List<Location> getHollowCube(Location min, Location max, double accuracy) {
        List<Location> result = Lists.newArrayList();
        World world = min.getWorld();
        double minX = min.getBlockX();
        double minY = min.getBlockY();
        double minZ = min.getBlockZ();
        double maxX = max.getBlockX();
        double maxY = max.getBlockY();
        double maxZ = max.getBlockZ();

        for (double x = minX; x <= maxX; x += accuracy) {
            for (double y = minY; y <= maxY; y += accuracy) {
                for (double z = minZ; z <= maxZ; z += accuracy) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }
        return result;
    }

    public static Location max(Location a, Location b) {
        if (a.getX() > b.getX() &&
                a.getY() > b.getY() &&
                a.getZ() > b.getZ()) {
            return a;
        }
        return b;
    }

    public static Location min(Location a, Location b) {
        if (a.getX() < b.getX() &&
                a.getY() < b.getY() &&
                a.getZ() < b.getZ()) {
            return a;
        }
        return b;
    }

    public static double yawToRotation(float yaw) {
        double rotation = (yaw - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        return rotation;
    }

    public static String floor(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setGroupingUsed(false);
        return decimalFormat.format(number);
    }
}
