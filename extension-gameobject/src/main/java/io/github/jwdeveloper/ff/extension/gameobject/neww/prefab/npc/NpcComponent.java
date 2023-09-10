package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.npc;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.TransformationUtility;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.util.Vector;


@Injection(lifeTime = LifeTime.TRANSIENT)
public class NpcComponent extends GameComponent {
    private NpcBodyComponent bodyComponent;
    private NpcBrainComponent brainComponent;
    private NpcNameComponent nameComponent;

    //adsa
    @Override
    public void onInitialization(FluentApiSpigot api) {

        bodyComponent = gameobject().addComponent(NpcBodyComponent.class);
        brainComponent = gameobject().addComponent(NpcBrainComponent.class);
        nameComponent = gameobject().addComponent(NpcNameComponent.class);
    }


    @Override
    public void onUpdateAsync(double deltaTime) {
        transform().position().add(new Vector(0,0,1*deltaTime*0.005f));



        var rot = (float) (deltaTime*Math.PI);
      //  logger().info("ROTATION",rot);

        var a = TransformationUtility.create(bodyComponent.getRightHand().getTransformation()).setLeftRotation(rot,0,0).setRightRotation(0,0,0).build();

      bodyComponent.getRightHand().setTransformation(a);
    }

    @Override
    public void onDestroy() {
        FluentLogger.LOGGER.info("Deseling object");
    }
}
