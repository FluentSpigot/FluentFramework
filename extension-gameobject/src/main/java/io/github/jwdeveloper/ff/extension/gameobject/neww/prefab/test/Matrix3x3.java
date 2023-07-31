package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.test;


import org.bukkit.util.Vector;

public class Matrix3x3 {
    private double[][] matrix;

    public Matrix3x3() {
        matrix = new double[3][3];
    }

    public Matrix3x3(double[][] values) {
        matrix = new double[3][3];
        if (values.length == 3 && values[0].length == 3) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    matrix[i][j] = values[i][j];
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid matrix dimensions.");
        }
    }

    public static Matrix3x3 identity() {
        Matrix3x3 identityMatrix = new Matrix3x3();
        for (int i = 0; i < 3; i++) {
            identityMatrix.matrix[i][i] = 1.0;
        }
        return identityMatrix;
    }

    public double get(int row, int column) {
        return matrix[row][column];
    }

    public void set(int row, int column, double value) {
        matrix[row][column] = value;
    }

    public Vector getColumn(int column) {
        if (column < 0 || column >= 3) {
            throw new IllegalArgumentException("Invalid column index.");
        }
        return new Vector(matrix[0][column], matrix[1][column], matrix[2][column]);
    }

    public void setColumn(int column, Vector vector) {
        if (column < 0 || column >= 3) {
            throw new IllegalArgumentException("Invalid column index.");
        }
        matrix[0][column] = vector.getX();
        matrix[1][column] = vector.getY();
        matrix[2][column] = vector.getZ();
    }

    public Matrix3x3 multiply(Matrix3x3 other) {
        Matrix3x3 result = new Matrix3x3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result.matrix[i][j] += matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return result;
    }
}