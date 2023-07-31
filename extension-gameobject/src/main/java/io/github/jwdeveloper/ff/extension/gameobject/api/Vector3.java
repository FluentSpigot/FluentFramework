package io.github.jwdeveloper.ff.extension.gameobject.api;

import lombok.Getter;

public class Vector3 {

    private double x;

    private double y;

    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vector3 add(double x, double y, double z) {
        x(this.x+x);
        y(this.y+y);
        z(this.z+z);
        return this;
    }


    public double x()
    {
        return x;
    }

    public double y()
    {
        return y;
    }

    public double z()
    {
        return z;
    }

    public void x(double x)
    {
          this.x = x;
    }
    public void y(double y)
    {
        this.y = y;
    }
    public void z(double z)
    {
        this.z = z;
    }

    public Vector3 add(double value) {
        return add(value, value, value);
    }

    public Vector3 add(Vector3 vector3) {
        return add(vector3.x, vector3.y, vector3.z);
    }

    public Vector3 clone() {
        return new Vector3(x, y, z);
    }


    public static Vector3 of(double x, double y, double z) {
        return new Vector3(x, y, z);
    }

    public static Vector3 of(float x, float y, float z) {
        return new Vector3(x, y, z);
    }

    public static Vector3 of(int x, int y, int z) {
        return new Vector3(x, y, z);
    }

    public static Vector3 of(double value) {
        return new Vector3(value, value, value);
    }

    public static Vector3 of(float value) {
        return new Vector3(value, value, value);
    }

    public static Vector3 of(int value) {
        return new Vector3(value, value, value);
    }
}
