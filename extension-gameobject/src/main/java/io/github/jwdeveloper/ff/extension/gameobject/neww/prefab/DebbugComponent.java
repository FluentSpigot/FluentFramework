package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab;

import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;


@Injection(lifeTime = LifeTime.TRANSIENT)
public class DebbugComponent extends GameComponent
{
    @Override
    public void onInitialization(FluentApiSpigot api)
    {

        logger().success(" initialized");
    }

    @Override
    public void onEnable() {
      logger().success(" enabled");
    }


    @Override
    public void onUpdateAsync(double deltaTime) {
        logger().success(" onUpdateAsync");
    }

    @Override
    public void onPhisicsAsync() {
        logger().success(" onPhisicsAsync");
    }

    @Override
    public void onRender() {
        logger().success(" onRender");
    }

    @Override
    public void onPreRenderAsync() {
        logger().success(" onPreRenderAsync");
    }
}
