package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing;


import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.ComponentData;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import lombok.SneakyThrows;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class DebbugBoxComponent extends GameComponent {
    @ComponentData
    public Material material = Material.RED_CONCRETE;
    @ComponentData
    public Color color = Color.RED;

    @ComponentData
    public float thinkness = 0.05f;

    public record RenderDto(int index, Location location, Transformation transformation) {
    }

    private BlockDisplay[] entitis;
    private List<RenderDto> renderLocations;


    @SneakyThrows
    public void setColor(Color color)
    {

    }


    @Override
    public void onInitialization(FluentApiSpigot api) {
        var location = transform().toLocation();
        entitis = new BlockDisplay[12];
        renderLocations = new ArrayList<>(12);
        for (var i = 0; i < 12; i++)
        {
            entitis[i] = LineDisplayFactory.createNew(location,color);
        }
    }

    @Override
    public void onPreRenderAsync()
    {
        renderLocations.clear();
        var worldTranslataion = worldTransform();
        var location = worldTranslataion.toLocation();
        var scale = worldTranslataion.scale();

        var p0 =worldTranslataion.position().toLocation(location.getWorld());
        var tr0 = getTransformation(scale.clone().setY(thinkness).setZ(thinkness));
        renderLocations.add(new RenderDto(0, p0, tr0));

        var p1 = worldTranslataion.position().toLocation(location.getWorld());
        var tr1 = getTransformation(scale.clone().setY(thinkness).setX(thinkness));
        renderLocations.add(new RenderDto(1, p1, tr1));



        var p2 =worldTranslataion.position().clone().add(new Vector(0,0,scale.getZ())).toLocation(location.getWorld());
        var tr2 = getTransformation(scale.clone().setY(thinkness).setZ(thinkness));
        renderLocations.add(new RenderDto(2, p2, tr2));


        var p3 = worldTranslataion.position().clone().add(new Vector(scale.getX(),0,0)).toLocation(location.getWorld());
        var tr3 = getTransformation(scale.clone().setY(thinkness).setX(thinkness));
        renderLocations.add(new RenderDto(3, p3, tr3));



        var p4 = worldTranslataion.position().clone().add(new Vector(0,scale.getY(),0)).toLocation(location.getWorld());
        var tr4 = getTransformation(scale.clone().setY(thinkness).setZ(thinkness));
        renderLocations.add(new RenderDto(4, p4, tr4));

        var p5 = worldTranslataion.position().clone().clone().add(new Vector(0,scale.getY(),0)).toLocation(location.getWorld());
        var tr5 = getTransformation(scale.clone().setY(thinkness).setX(thinkness));
        renderLocations.add(new RenderDto(5, p5, tr5));



        var p6 =worldTranslataion.position().clone().add(new Vector(0,scale.getY(),scale.getZ())).toLocation(location.getWorld());
        var tr6 = getTransformation(scale.clone().setY(thinkness).setZ(thinkness));
        renderLocations.add(new RenderDto(6, p6, tr6));


        var p7 = worldTranslataion.position().clone().add(new Vector(scale.getX(),scale.getY(),0)).toLocation(location.getWorld());
        var tr7 = getTransformation(scale.clone().setY(thinkness).setX(thinkness));
        renderLocations.add(new RenderDto(7, p7, tr7));



        var p8 =worldTranslataion.position().clone().add(new Vector(0,0,0)).toLocation(location.getWorld());
        var tr8 = getTransformation(scale.clone().setX(thinkness).setZ(thinkness));
        renderLocations.add(new RenderDto(8, p8, tr8));

        var p9 = worldTranslataion.position().clone().clone().add(new Vector(scale.getZ(),0,scale.getZ())).toLocation(location.getWorld());
        var tr9 = getTransformation(scale.clone().setX(thinkness).setZ(thinkness));
        renderLocations.add(new RenderDto(9, p9, tr9));



        var p10 = worldTranslataion.position().clone().add(new Vector(0,0,scale.getZ())).toLocation(location.getWorld());
        var tr10 = getTransformation(scale.clone().setX(thinkness).setZ(thinkness));
        renderLocations.add(new RenderDto(10, p10, tr10));


        var p11 =worldTranslataion.position().clone().add(new Vector(scale.getX(),0,0)).toLocation(location.getWorld());
        var tr11 = getTransformation(scale.clone().setX(thinkness).setZ(thinkness));
        renderLocations.add(new RenderDto(11, p11, tr11));

    }

    public Transformation getTransformation(Vector scale) {
        var a = new Vector3f(0, 0, 0);
        var b = new AxisAngle4f(0, 0, 0, 1);
        var c = scale.toVector3f();
        var d = new AxisAngle4f(0, 0, 0, 1);
        return new Transformation(a, b, c, d);
    }

    @Override
    public void onRender() {
        for (var renderData : renderLocations) {
            entitis[renderData.index].teleport(renderData.location);
            entitis[renderData.index].setTransformation(renderData.transformation);
        }
    }

    @Override
    public void onDestroy() {
        for(var box : entitis)
        {
            box.remove();
        }
    }
}
