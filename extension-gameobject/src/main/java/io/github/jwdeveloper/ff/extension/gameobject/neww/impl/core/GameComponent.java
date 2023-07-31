package io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core;

import io.github.jwdeveloper.ff.core.common.java.EmptyException;
import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.Component;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.ComponentsManager;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.GameObject;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.Transportation;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.exception.GameObjectException;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class GameComponent implements Component {
    private GameObject gameObject;

    @Getter
    private boolean componentActive = true;


    protected void setComponentActive(boolean componentActive) {
        this.componentActive = componentActive;
    }

    @Override
    public void onInitialization(FluentApiSpigot api) {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDestroy() {

    }


    public void onPhisicsAsync() {

    }


    public void onUpdateAsync(double deltaTime) {

    }

    public void onPreRenderAsync() {

    }

    public void onRender() {

    }

    public final void init(GameObject gameObject) {
        this.gameObject = gameObject;
    }


    @Override
    public final GameObject gameobject() {
        return gameObject;
    }

    @Override
    public final Transportation transform() {
        return gameObject.transform();
    }


    public final Transportation worldTransform() {
        return gameObject.worldTransform();
    }

    @Override
    public final ComponentsManager components() {
        return gameObject.components();
    }

    @Override
    public final PluginLogger logger() {
        return gameObject.logger();
    }


    public GameObjectException throwError(String message) {
        throw new GameObjectException(message);
    }


    private Map<String,Waiter> waiters = new HashMap<>();



    public boolean waitForSeconds(float seconds, String tag)
    {
        if(!waiters.containsKey(tag))
        {
           var waiter = new Waiter();
           waiter.currentTime = 0;
           waiter.yield = 0.1f;
           waiter.maxTime = seconds;

            waiters.put(tag,waiter);
            throw new EmptyException();
        }

        var waiter = waiters.get(tag);
        if(waiter.isDone)
        {
            waiters.remove(tag);
            throw new EmptyException();
        }

        waiter.currentTime += waiter.yield;

        if(waiter.currentTime < waiter.maxTime)
        {
            throw new EmptyException();
        }
        waiter.isDone = true;
        return true;
    }


    public class Waiter
    {
        private String tag;
        private float currentTime;
        private float maxTime;
        private float yield;
        private boolean isDone;
    }

}
