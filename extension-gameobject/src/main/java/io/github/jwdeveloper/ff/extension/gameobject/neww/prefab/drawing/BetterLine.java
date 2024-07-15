package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing;


import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.AngleCalculator;
import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class BetterLine extends GameComponent {
    BlockDisplay entity;


    @Setter
    Vector direction;


    @Override
    public void onEnable() {
        entity = (BlockDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.BLOCK_DISPLAY);
        entity.setGlowing(true);
        entity.setViewRange(30);
        entity.setInterpolationDuration(1);
        entity.setInterpolationDelay(1);
        entity.setShadowStrength(0);
        entity.setBrightness(new Display.Brightness(15, 15));
        entity.setBlock(Material.STONE.createBlockData());
        entity.setGlowColorOverride(Color.PURPLE);
        direction = new Vector(0, 1, 0);
    }


    @Override
    public void onUpdateAsync(double deltaTime) {
        var angle = AngleCalculator.calculate(transform().position(), direction);

        var distance = transform().position().distance(direction);

        transform().scale(10 * distance * 0.1f, 0.1f, 0.1f);
        transform().rotation(0, 0, 1.5);
    }

    int i =0;
    @Override
    public void onRender() {
        entity.teleport(worldTransform().toLocation());




        var dega = 180;
        var a = 180f/360f;

        var t = TransformationUtility.create(transform()
                .toBukkitTransformation())
                .setLeftRotation(test(0,Math.PI,0))
                .build();

        i++;
        entity.setTransformation(t);
    }

    @Override
    public void onDestroy() {
        entity.remove();
    }

    public static Quaternionf eulerToQuaternion(double roll, double pitch, double yaw) {
        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);

        double w = cr * cp * cy + sr * sp * sy;
        double x = sr * cp * cy - cr * sp * sy;
        double y = cr * sp * cy + sr * cp * sy;
        double z = cr * cp * sy - sr * sp * cy;

        return new Quaternionf(w, x, y, z);
    }

    public static Quaternionf test(double roll, double pitch, double yaw)
    {
        double angle;
        double sinRoll, sinPitch, sinYaw, cosRoll, cosPitch, cosYaw;
        angle = pitch * 0.5f;
        sinPitch = Math.sin(angle);
        cosPitch = Math.cos(angle);
        angle = roll * 0.5f;
        sinRoll = Math.sin(angle);
        cosRoll = Math.cos(angle);
        angle = yaw * 0.5f;
        sinYaw = Math.sin(angle);
        cosYaw = Math.cos(angle);

        // variables used to reduce multiplication calls.
        double cosRollXcosPitch = cosRoll * cosPitch;
        double sinRollXsinPitch = sinRoll * sinPitch;
        double cosRollXsinPitch = cosRoll * sinPitch;
        double sinRollXcosPitch = sinRoll * cosPitch;

       var w = (cosRollXcosPitch * cosYaw - sinRollXsinPitch * sinYaw);
       var x = (cosRollXcosPitch * sinYaw + sinRollXsinPitch * cosYaw);
       var  y = (sinRollXcosPitch * cosYaw + cosRollXsinPitch * sinYaw);
       var z = (cosRollXsinPitch * cosYaw - sinRollXcosPitch * sinYaw);

       var q = new Quaternionf(x,y,z,w);

        return    q.normalize();
    }
}
