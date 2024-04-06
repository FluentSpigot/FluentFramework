package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.grid;

import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.TransformationUtility;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing.LineDisplayFactory;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class CardatianGridComponent extends GameComponent {

    private final float lineWidth = 150;
    private final float lineThickness = 0.01f;


    private BlockDisplay xLine;
    private BlockDisplay yLine;
    private BlockDisplay zLine;
    private List<BlockDisplay> members;

    private List<BlockDisplay> poolingObjects;

    private boolean scaleWithPlayer = true;

    private float currentThickNess = 0.04f;

    @Override
    public void onEnable() {
        xLine = LineDisplayFactory.createNew(transform().toLocation(), Color.RED);
        yLine = LineDisplayFactory.createNew(transform().toLocation(), Color.GREEN);
        zLine = LineDisplayFactory.createNew(transform().toLocation(), Color.BLUE);


       /* var pLine = LineDisplayFactory.line(transform().toLocation(),Color.PURPLE);
        pLine.setThickness(0.1f);
        pLine.setWidth(1);
        pLine.setAxis(Axis.X);


        FluentApi.tasks().taskTimer(1, (x,b) ->
        {
            var rot = x*0.1f;
             pLine.getRotation().rotationY(rot);
            pLine.update(transform().toLocation().add(0,2,0));
        }).run();*/


        members = new ArrayList<>();
        members.add(xLine);
        members.add(yLine);
        members.add(zLine);

        for (var memeber : members) {
            memeber.setViewRange(lineWidth * 2);
            memeber.setInterpolationDuration(1);
            memeber.setInterpolationDelay(1);
        }

        poolingObjects = new ArrayList<>();
        for (var i = 0; i < 300; i++) {
            var ent = LineDisplayFactory.createNew(transform().toLocation(), Color.BLACK);
            ent.setViewRange(lineWidth);
            ent.setInterpolationDuration(1);
            ent.setInterpolationDelay(1);
            ent.setGlowing(false);
            poolingObjects.add(ent);
        }
    }


    @Override
    public void onUpdateAsync(double deltaTime) {
        var player = Bukkit.getOnlinePlayers().stream().toList().get(0);
        if (scaleWithPlayer) {
            var pLoc = player.getLocation();
            var thisLoc = transform().toLocation();
            var distance = (float) pLoc.distance(thisLoc);

            if (distance <= 0.1f) {
                this.currentThickNess = lineThickness;
            } else {
                this.currentThickNess = distance / 150f;
            }


        }
    }

    public List<Transformation> createTransformations() {
        var result = new ArrayList<Transformation>();
        var x = TransformationUtility.create(xLine.getTransformation()).setTranslation(-lineWidth/2, 0, 0).setScale(lineWidth , currentThickNess, currentThickNess).build();
        var y = TransformationUtility.create(yLine.getTransformation()).setTranslation(0, -lineWidth/2, 0).setScale(currentThickNess, lineWidth, currentThickNess).build();
        var z = TransformationUtility.create(zLine.getTransformation()).setTranslation(0, 0, -lineWidth/2).setScale(currentThickNess, currentThickNess, lineWidth ).build();

        result.add(x);
        result.add(y);
        result.add(z);

        return result;
    }

    @Override
    public void onRender() {

        waitForSeconds(2, "render");
        var index = 0;
        var trans = createTransformations();
        for (var member : members) {
            member.teleport(transform().toLocation());
            member.setTransformation(trans.get(index));
            index++;
        }

        var xTrans = trans.get(0);
        var zTrans = trans.get(2);


        //logger().info("SIAM",xTrans.getScale().z() );
        if (xTrans.getScale().z() > lineThickness) {
            xTrans = TransformationUtility.create(xTrans).setScaleZ(lineThickness).setScaleY(lineThickness).setScaleX(lineWidth).setTranslation(new Vector3f(-lineWidth / 2, 0, 0)).build();
            zTrans = TransformationUtility.create(zTrans).setScaleX(lineThickness).setScaleY(lineThickness).setScaleZ(lineWidth).setTranslation(new Vector3f(0, 0, -lineWidth / 2)).build();
        }


        var offset = -(poolingObjects.size() / 4);

        for (var i = 0; i < poolingObjects.size(); i += 2) {
            var xObj = poolingObjects.get(i);
            var zObj = poolingObjects.get(i + 1);

            xObj.setTransformation(xTrans);
            xObj.teleport(transform().toLocation().add(0, 0, offset));

            zObj.setTransformation(zTrans);
            zObj.teleport(transform().toLocation().add(offset, 0, 0));

            offset++;
            if (offset == 0) {
                offset++;
            }
        }


    }

    @Override
    public void onDestroy() {
        members.forEach(Entity::remove);
        poolingObjects.forEach(Entity::remove);
    }
}
