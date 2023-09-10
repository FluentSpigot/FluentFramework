package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing;

import lombok.SneakyThrows;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;

public class LineDisplayFactory
{

    public static LineDisplay line(Location location, Color color)
    {
        return new LineDisplay(location,color);
    }


    public static BlockDisplay createNew(Location location, Color color, Material material)
    {
        var result  = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
        result.setBlock(material.createBlockData());
        result.setGlowColorOverride(color);
        result.setShadowStrength(0);
        result.setBrightness(new Display.Brightness(15, 15));
        result.setGlowing(true);
        result.setInterpolationDelay(0);
        result.setInterpolationDuration(5);
        return result;
    }


    public static BlockDisplay createNew(Location location, Color color)
    {
        var result  = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
        var material = getColorMaterial(color);
        result.setBlock(material.createBlockData());
        result.setGlowColorOverride(color);
        result.setShadowStrength(0);
        result.setBrightness(new Display.Brightness(15, 15));
        result.setGlowing(true);
        result.setInterpolationDelay(0);
        result.setInterpolationDuration(5);
        return result;
    }



    @SneakyThrows
    public static Material getColorMaterial(Color color)
    {
        for(var field : Color.class.getDeclaredFields())
        {
            if(!field.getType().equals(Color.class))
            {
                continue;
            }
            field.setAccessible(true);
            var value = field.get(null);
            if(color != value)
            {
                continue;
            }
            try
            {
                var name = field.getName();
                var materialName = name+"_CONCRETE";
                var material =Material.getMaterial(materialName);

                return material;
            }
            catch (Exception e)
            {
                continue;
            }
        }
        return Material.WHITE_CONCRETE;
    }
}
