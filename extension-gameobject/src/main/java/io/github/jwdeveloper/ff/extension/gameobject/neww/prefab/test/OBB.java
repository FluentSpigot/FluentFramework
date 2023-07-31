package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.test;

import io.github.jwdeveloper.ff.extension.gameobject.api.Vector3;
import org.bukkit.util.Vector;

public class OBB
{
    private Vector center;    // Center position of the OBB
    private Vector size;      // Dimensions of the OBB (length, width, height)
    private Matrix3x3 orientation;  // Rotation matrix representing the orientation of the OBB

    public OBB(Vector center, Vector size, Matrix3x3 orientation) {
        this.center = center;
        this.size = size;
        this.orientation = orientation;
    }

    public Vector getCenter() {
        return center;
    }

    public Vector getSize() {
        return size;
    }

    public Matrix3x3 getOrientation() {
        return orientation;
    }

    public Vector[] getCorners() {
        Vector[] corners = new Vector[8];
        Vector[] localCorners = {
                new Vector(-0.5, -0.5, -0.5),
                new Vector(-0.5, -0.5, 0.5),
                new Vector(-0.5, 0.5, -0.5),
                new Vector(-0.5, 0.5, 0.5),
                new Vector(0.5, -0.5, -0.5),
                new Vector(0.5, -0.5, 0.5),
                new Vector(0.5, 0.5, -0.5),
                new Vector(0.5, 0.5, 0.5)
        };

        for (int i = 0; i < 8; i++) {
         //   corners[i] = center.add(orientation.multiply(localCorners[i].multiply(size)));
        }

        return corners;
    }

    public boolean intersects(OBB other) {
        Vector[] corners1 = this.getCorners();
        Vector[] corners2 = other.getCorners();

        for (int i = 0; i < 3; i++) {
            Vector axis1 = orientation.getColumn(i).normalize();
            Vector axis2 = other.getOrientation().getColumn(i).normalize();

            double[] proj1 = projectOntoAxis(corners1, axis1);
            double[] proj2 = projectOntoAxis(corners2, axis1);
            double[] proj3 = projectOntoAxis(corners1, axis2);
            double[] proj4 = projectOntoAxis(corners2, axis2);

            if (!overlap(proj1, proj2) || !overlap(proj3, proj4)) {
                return false;
            }
        }

        return true;
    }

    private double[] projectOntoAxis(Vector[] points, Vector axis) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (Vector point : points) {
            double projection = point.dot(axis);
            if (projection < min) {
                min = projection;
            }
            if (projection > max) {
                max = projection;
            }
        }

        return new double[]{min, max};
    }

    private boolean overlap(double[] proj1, double[] proj2) {
        return (proj1[0] <= proj2[1] && proj1[1] >= proj2[0])
                || (proj2[0] <= proj1[1] && proj2[1] >= proj1[0]);
    }

    // Example usage
    public static void main(String[] args) {
        Vector center1 = new Vector(0, 0, 0);
        Vector size1 = new Vector(1, 1, 1);
        Matrix3x3 orientation1 = Matrix3x3.identity(); // No rotation

        OBB obb1 = new OBB(center1, size1, orientation1);

        Vector center2 = new Vector(2, 2, 2);
        Vector size2 = new Vector(1, 1, 1);
        Matrix3x3 orientation2 = new Matrix3x3(); // Custom rotation matrix
      //  orientation2.set(0,0, new Vector(1, 0, 0));
      //  orientation2.set(1,0, new Vector(0, 1, 0));
      //  orientation2.set(2,0, new Vector(0, 0, 1));

        OBB obb2 = new OBB(center2, size2, orientation2);

        boolean intersects = obb1.intersects(obb2);
        System.out.println("Intersects: " + intersects);
    }
}
