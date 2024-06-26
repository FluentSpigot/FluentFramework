package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab;


import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.GameObject;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.GameObjectFactory;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing.*;
import io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.grid.CardatianGridComponent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.util.Vector;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class DebbugCube extends GameComponent {
    DebbugBoxComponent outline;
    CardatianGridComponent gridComponent;
    ItemPointDisplay point;

    GameObject parent;

    GameObject child;

    TransformationInfoComponent tracking;


    @Override
    public void onInitialization(FluentApiSpigot api) {
        transform().position().add(new Vector(0, 1, 0));

        gridComponent = gameobject().addComponent(CardatianGridComponent.class);
        outline = gameobject().addComponent(DebbugBoxComponent.class);

        tracking = gameobject().addComponent(TransformationInfoComponent.class);


        parent = GameObjectFactory.instance("parent", gridComponent.gameobject(), ItemPointDisplay.class);
        point = parent.components().getComponent(ItemPointDisplay.class);


        child = GameObjectFactory.instance("child", parent, ItemPointDisplay.class);


        tracking.setShowRotation(true);
        tracking.setShowTransation(false);


    }

    @Override
    public void onEnable() {

        point.transform().scale(1, 1, 1);

        point.transform().rotation(0,45,0);

        child.transform().scale(0.5,0.5,0.5);
        child.transform().position(2,2,2);

        tracking.setTracking(child);
        tracking.setVisible(true);
    }


    float i = 0;

    @Override
    public void onRender() {
       waitForSeconds(1f,"X");
       logger().info("Simea",i,this.toString());

       var angle = i*Math.PI/180;

       point.transform().rotation().setY(i);


       var p1 = child.transform().position().clone();
       p1.rotateAroundY(angle);
       p1.subtract(child.transform().position());


       child.transform().translation(p1.getX(),p1.getY(),p1.getZ());
        child.transform().rotation().setY(i+10);

        i += 10;
    }

    @Override
    public void onDestroy() {
        child.destroy();
    }
}
