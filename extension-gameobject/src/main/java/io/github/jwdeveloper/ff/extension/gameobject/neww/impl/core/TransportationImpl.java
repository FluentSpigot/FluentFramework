package io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core;

import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.Transportation;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.TransformationUtility;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

public class TransportationImpl implements Transportation {

    private Vector position;
    private Vector translation;
    private Vector rotation;
    private Vector scale;
    private World world;

    public TransportationImpl(Vector position,Vector translation,  Vector rotation, Vector scale, World world) {
        this.position = position;
        this.translation =translation;
        this.rotation = rotation;
        this.scale = scale;
        this.world = world;
    }

    public TransportationImpl() {
        this.position = new Vector(0,0,0);
        this.rotation= new Vector(0,0,0);
        this.scale= new Vector(1,1,1);
        this.translation = new Vector(0,0,0);
    }

    @Override
    public Vector position() {
        return position;
    }

    @Override
    public Vector translation() {
        return translation;
    }

    @Override
    public Vector translation(double x, double y, double z)
    {
        return translation().setX(x).setY(y).setZ(z);
    }

    @Override
    public Vector position(double x, double y, double z)
    {
        return position().setX(x).setY(y).setZ(z);
    }

    @Override
    public Vector rotation() {
        return rotation;
    }

    @Override
    public Vector rotation(double x, double y, double z) {



        return rotation().setX(x).setY(y).setZ(z);
    }

    @Override
    public Vector scale() {
        return scale;
    }

    @Override
    public Vector scale(double x, double y, double z) {
        return scale().setX(x).setY(y).setZ(z);
    }

    @Override
    public World world() {
        return world;
    }

    @Override
    public void setWorld(World world) {
          this.world = world;
    }

    @Override
    public Location toLocation() {
        return new Location(world, position.getX(), position.getY(),position.getZ());
    }


    @Override
    public Transformation toBukkitTransformation() {
        return TransformationUtility.create()
                .setTranslation(translation())
                .setLeftRotation(rotation())
                .setScale(scale()).build();
    }

    @Override
    public Transportation clone() {
        return new TransportationImpl(this.position,this.translation, this.rotation,this.scale,this.world);
    }

    public Transportation merge(Transportation second)
    {
        var pos = this.position.clone();
        var trans = this.translation.clone();
        var rot = this.rotation.clone();



        var scl = this.scale.clone();

        var a = pos.add(second.position());
        var b = rot.add(second.rotation());
        var c = scl.multiply(second.scale());
        var d = trans.add(second.translation());

        return new TransportationImpl(a,d,b,c, second.world());
    }

    public Transportation copy()
    {
        return new TransportationImpl(this.position,this.translation, this.rotation, this.scale, this.world);
    }
}
