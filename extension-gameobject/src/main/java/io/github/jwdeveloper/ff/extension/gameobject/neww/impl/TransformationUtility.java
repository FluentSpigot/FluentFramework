package io.github.jwdeveloper.ff.extension.gameobject.neww.impl;

import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TransformationUtility {

    public static TransformationBuilder create(Transformation a) {
        return new TransformationBuilder()
                .setLeftRotation(a.getLeftRotation())
                .setRightRotation(a.getRightRotation())
                .setScale(a.getScale())
                .setTranslation(a.getTranslation());
    }

    public static TransformationBuilder create() {

        return new TransformationBuilder();
    }




    public static class TransformationBuilder {
        private Vector3f translation = new Vector3f(0, 0, 0);

        private Quaternionf leftRotation = new Quaternionf(0, 0, 0, 1);

        private Vector3f scale = new Vector3f(1, 1, 1);

        private Quaternionf rightRotation = new Quaternionf(0, 0, 0, 1);


        public TransformationBuilder addTranslation(Vector3f a) {
            this.translation.add(a.x, a.y, a.z);
            return this;
        }

        public TransformationBuilder addTranslation(int x, int y, int z) {
            this.translation.add(x, y,z);
            return this;
        }


        public TransformationBuilder setTranslation(Vector a) {
            this.translation = new Vector3f((float) a.getX(),(float)a.getY(),(float)a.getZ());
            return this;
        }

        public TransformationBuilder setTranslation(Vector3f a) {
            this.translation = a;
            return this;
        }

        public TransformationBuilder setScale(Vector3f a) {
            this.scale = a;
            return this;
        }

        public TransformationBuilder setLeftRotation(Quaternionf a) {
            this.leftRotation = a;
            return this;
        }


        public TransformationBuilder setRightRotation(Quaternionf a) {
            this.rightRotation = a;
            return this;
        }


        public TransformationBuilder setTranslation(float x, float y, float z) {
            this.translation = new Vector3f(x, y, z);
            return this;
        }

        public TransformationBuilder setScaleX(float x)
        {
            this.scale = new Vector3f(x, this.scale.y, this.scale.z);
            return this;
        }

        public TransformationBuilder setScale(Vector vector)
        {
            this.scale = new Vector3f((float) vector.getX(),(float)  vector.getY(), (float) vector.getZ());
            return this;
        }

        public TransformationBuilder setScaleZ(float z)
        {
            this.scale = new Vector3f(this.scale.x, this.scale.y, z);
            return this;
        }

        public TransformationBuilder setScaleY(float y)
        {
            this.scale = new Vector3f(this.scale.x, y, this.scale.z);
            return this;
        }

        public TransformationBuilder setScale(float x, float y, float z) {
            this.scale = new Vector3f(x, y, z);
            return this;
        }

        public TransformationBuilder setLeftRotation(float x, float y, float z) {
           // this.leftRotation = new Quaternionf(x, y, z, i);
        //  this.leftRotation.rotationX(x);
            this.leftRotation.rotationYXZ(y,x,z);
         //   this.leftRotation.rotationZ(z);
            return this;
        }

        public TransformationBuilder setLeftRotation(Vector vector) {
            // this.leftRotation = new Quaternionf(x, y, z, i);
            //  this.leftRotation.rotationX(x);
            this.leftRotation.rotationXYZ((float) vector.getX(),(float) vector.getY(),(float) vector.getZ());
            //   this.leftRotation.rotationZ(z);
            return this;
        }

        public TransformationBuilder addRightRotation(float x, float y, float z, float i) {
            this.rightRotation = new Quaternionf(this.rightRotation.x + x, this.rightRotation.y + y, this.rightRotation.z + z, i);
            return this;
        }


        public TransformationBuilder setRightRotation(float x, float y, float z) {
            this.rightRotation.rotationYXZ(y,x,z);
            return this;
        }


        public Transformation build() {
            return new Transformation(translation, leftRotation, scale, rightRotation);
        }

    }

}
