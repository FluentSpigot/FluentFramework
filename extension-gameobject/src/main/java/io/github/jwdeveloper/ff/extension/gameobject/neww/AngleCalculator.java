package io.github.jwdeveloper.ff.extension.gameobject.neww;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.extension.gameobject.api.Vector3;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

public class AngleCalculator
{
    public static void main(String[] args) {
        // Example usage
        Vector3f positionA = new Vector3f(0, 0, 0);
        Vector3f positionB = new Vector3f(0, -5.0f, 0);
        Vector3f direction = calculateDirection(positionA, positionB);
        double azimuthAngleX = calculateAzimuthAngleX(direction);
        double elevationAngleX = calculateElevationAngleX(direction);
        double azimuthAngleY = calculateAzimuthAngleY(direction);
        double elevationAngleY = calculateElevationAngleY(direction);
        double azimuthAngleZ = calculateAzimuthAngleZ(direction);
        double elevationAngleZ = calculateElevationAngleZ(direction);

        System.out.println("Azimuth angle (X-axis): " + azimuthAngleX);
        System.out.println("Elevation angle (X-axis): " + elevationAngleX);
        System.out.println("Azimuth angle (Y-axis): " + azimuthAngleY);
        System.out.println("Elevation angle (Y-axis): " + elevationAngleY);
        System.out.println("Azimuth angle (Z-axis): " + azimuthAngleZ);
        System.out.println("Elevation angle (Z-axis): " + elevationAngleZ);


        FluentLogger.LOGGER.info("ONE","X", Math.toDegrees(azimuthAngleX), "Y",  Math.toDegrees(azimuthAngleY), "Z", Math.toDegrees(elevationAngleZ));
        FluentLogger.LOGGER.info("TWO","X", elevationAngleX, "Y", elevationAngleY, "Z", elevationAngleZ);
    }

    public static Vector calculate(Vector a, Vector b)
    {
        var positionA = a.toVector3f();
        var positionB = b.toVector3f();

        var direction = calculateDirection(positionA, positionB);
        var azimuthAngleX = calculateAzimuthAngleX(direction);
        var azimuthAngleY = calculateAzimuthAngleY(direction);
        var azimuthAngleZ = calculateAzimuthAngleZ(direction);

        double elevationAngleX = calculateElevationAngleX(direction);
        double elevationAngleY = calculateElevationAngleY(direction);
        double elevationAngleZ = calculateElevationAngleZ(direction);

        var x= Math.toDegrees(azimuthAngleX);
        var y= Math.toDegrees(azimuthAngleY);
        var z= Math.toDegrees(azimuthAngleZ);

        var result = new Vector(x,y,z);
       // FluentLogger.LOGGER.info("==================================");
      //  FluentLogger.LOGGER.info("A ",positionA.toString());
     //   FluentLogger.LOGGER.info("B ",positionB.toString());
    //    FluentLogger.LOGGER.info("OUT ",  result.getX(), result.getY(),result.getZ());
    //    FluentLogger.LOGGER.info("EVALUATIONJ ",  elevationAngleX,elevationAngleY,elevationAngleZ);

        return result;
    }

    public static Vector3f calculateDirection(Vector3f positionA, Vector3f positionB) {
        Vector3f direction = positionB.sub(positionA);
        return direction.normalize();
    }

    public static double calculateAzimuthAngleX(Vector3f direction) {
        return Math.atan2(direction.y, direction.x);
    }

    public static double calculateElevationAngleX(Vector3f direction) {
        return Math.atan2(direction.z, Math.sqrt(direction.x * direction.x + direction.y * direction.y));
    }

    public static double calculateAzimuthAngleY(Vector3f direction) {
        return Math.atan2(direction.z, direction.y);
    }

    public static double calculateElevationAngleY(Vector3f direction) {
        return Math.atan2(direction.x, Math.sqrt(direction.y * direction.y + direction.z * direction.z));
    }

    public static double calculateAzimuthAngleZ(Vector3f direction) {
        return Math.atan2(direction.y, direction.z);
    }

    public static double calculateElevationAngleZ(Vector3f direction) {
        return Math.atan2(direction.x, Math.sqrt(direction.y * direction.y + direction.z * direction.z));
    }
}
