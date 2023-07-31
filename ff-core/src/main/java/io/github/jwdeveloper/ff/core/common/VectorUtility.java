package io.github.jwdeveloper.ff.core.common;

import org.bukkit.util.Vector;

public class VectorUtility
{
    public static Vector lerp3(Vector pointA, Vector pointB, double time) {
        double lerpX = pointA.getX() + (pointB.getX() - pointA.getX()) * time;
        double lerpY = pointA.getY() + (pointB.getY() - pointA.getY()) * time;
        double lerpZ = pointA.getZ() + (pointB.getZ() - pointA.getZ()) * time;

        return new Vector(lerpX, lerpY, lerpZ);
    }

    public static Vector rotate(Vector current, Vector rotation)
    {

        double angleX= rotation.getX();
        double angleY =  rotation.getY();
        double angleZ = rotation.getZ();
        double cosThetaX = Math.cos(angleX);
        double sinThetaX = Math.sin(angleX);

        double cosThetaY = Math.cos(angleY);
        double sinThetaY = Math.sin(angleY);

        double cosThetaZ = Math.cos(angleZ);
        double sinThetaZ = Math.sin(angleZ);


        double rotatedX = current.getX();
        double rotatedY = cosThetaX * current.getY() - sinThetaX * current.getZ();
        double rotatedZ = sinThetaX * current.getY() + cosThetaX *current.getZ();


        double tempX = cosThetaY * rotatedX + sinThetaY * rotatedZ;
        rotatedZ = -sinThetaY * rotatedX + cosThetaY * rotatedZ;
        rotatedX = tempX;


        tempX = cosThetaZ * rotatedX - sinThetaZ * rotatedY;
        rotatedY = sinThetaZ * rotatedX + cosThetaZ * rotatedY;
        rotatedX = tempX;


        return new Vector(rotatedX,rotatedY,rotatedZ);
    }

    public static   Vector min(Vector v1, Vector v2) {
        double minX = Math.min(v1.getX(), v2.getX());
        double minY = Math.min(v1.getY(), v2.getY());
        double minZ = Math.min(v1.getZ(), v2.getZ());

        return new Vector(minX, minY, minZ);
    }

    public static  Vector max(Vector v1, Vector v2) {
        double maxX = Math.max(v1.getX(), v2.getX());
        double maxY = Math.max(v1.getY(), v2.getY());
        double maxZ = Math.max(v1.getZ(), v2.getZ());

        return new Vector(maxX, maxY, maxZ);
    }
}
