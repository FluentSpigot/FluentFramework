package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing;

import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Axis;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.joml.Quaternionf;

import java.io.Closeable;


public class LineDisplay implements Closeable
{
    private BlockDisplay entity;

    @Getter
    @Setter
    private float width = 1;

    @Getter
    @Setter
    private float thickness =1;

    @Getter
    @Setter
    private Axis axis;

    @Getter
    private Location location;


    @Getter
    @Setter
    private Quaternionf rotation;

    @Getter
    @Setter
    private Color color;

    public LineDisplay(Location location, Color color)
    {
        this.location = location;
        this.color = color;
        rotation = new Quaternionf();
    }

    public BlockDisplay getEntity()
    {
        if(entity != null)
        {
            return entity;
        }
        entity = (BlockDisplay)location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
        entity.setGlowing(true);
        entity.setViewRange(width * 2);
        entity.setInterpolationDuration(1);
        entity.setInterpolationDelay(1);
        entity.setShadowStrength(0);
        entity.setBrightness(new Display.Brightness(15, 15));
        setColor(color);
        return entity;
    }

    public void setColor(Color color)
    {
        var material = LineDisplayFactory.getColorMaterial(color);
        var entity=  getEntity();
        entity.setGlowColorOverride(color);
        entity.setBlock(material.createBlockData());
    }

    public void update(Location location)
    {
        this.location = location;
        update();
    }

    public void update()
    {
        var entity = getEntity();
        var transformBuilder = TransformationUtility.create(entity.getTransformation());

        switch (this.axis)
        {
            case X -> transformBuilder.setTranslation(2,0,1).setLeftRotation(rotation).setRightRotation(rotation.mul(-1))
                    .setScale(thickness, thickness, thickness);
            case Y -> transformBuilder.setTranslation(0, -width, 0).setLeftRotation(rotation).setScale(thickness, width * 2, thickness);
            case Z -> transformBuilder.setTranslation(0, 0, -width).setLeftRotation(rotation).setScale(thickness, thickness, width * 2);
        }

        var transfomration = transformBuilder.build();

        entity.teleport(location.clone().add(-thickness/2,0,-thickness/2));
        entity.setTransformation(transfomration);
    }


    @Override
    public void close(){
        getEntity().remove();
    }
}
