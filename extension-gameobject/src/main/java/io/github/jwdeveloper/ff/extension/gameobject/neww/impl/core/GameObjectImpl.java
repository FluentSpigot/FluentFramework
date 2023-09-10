package io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core;

import io.github.jwdeveloper.ff.core.common.java.EmptyException;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.logger.plugin.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.*;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameObjectImpl implements GameObject {
    public final ComponentManagerImpl components;
    public final GameObjectMeta meta;
    public final EventManagerImpl events;
    public final TransportationImpl transportation;
    public final List<GameObject> children;
    private final PluginLogger logger;
    private GameObject parent;


    public GameObjectImpl() {
        children = new ArrayList<>();
        meta = new GameObjectMeta();
        events = new EventManagerImpl();
        transportation = new TransportationImpl();
        components = new ComponentManagerImpl(this);
        logger = new SimpleLogger(ChatColor.GREEN + "GameObject@" + Integer.toHexString(hashCode()) + ChatColor.WHITE);

        events.onInitialization(this::odOnInitialization);
        events.onEnable(this::doOnEnable);
        events.onPhisicAsync(this::doOnPhisicAsync);
        events.onUpdateAsync(this::doOnUpdateAsync);
        events.onBeforeRenderAsync(this::doOnPreRenderAsync);
        events.onRender(this::doOnRender);
        events.onDestroy(this::doOnDestroy);
    }

    @Override
    public void setVisible(boolean visible) {
        meta.setVisible(visible);
    }


    private void odOnInitialization(FluentApiSpigot fluentApiSpigot) {
        var toInitializeQueue = components.componentsToInitialize;
        while (!toInitializeQueue.isEmpty()) {
            var component = toInitializeQueue.poll();
            if (component == null) {
                break;
            }
            if (component instanceof GameComponent gc) {
                gc.init(this);
            }
            component.onInitialization(fluentApiSpigot);
        }


        for (var child : children) {
            var go = (GameObjectImpl) child;
            go.odOnInitialization(fluentApiSpigot);
        }
    }

    private void doOnEnable(Void fluentApiSpigot) {
        for (var component : components.getComponents()) {
            component.onEnable();
        }

        for (var child : children)
        {
            var go = (GameObjectImpl) child;
            go.doOnEnable(fluentApiSpigot);
        }
    }

    private void doOnPhisicAsync(Void deltaTime) {
        if (!meta().isActive()) {
            return;
        }

        for (var component : components.getComponents()) {
            if (!component.isComponentActive()) {
                return;
            }
            component.onPhisicsAsync();
        }

        for (var child : children) {
            var go = (GameObjectImpl) child;
            go.doOnPhisicAsync(deltaTime);
        }
    }


    private void doOnUpdateAsync(double deltaTime) {
        if (!meta().isActive()) {
            return;
        }

        for (var component : components.getComponents()) {

            if (!component.isComponentActive()) {
                return;
            }

            component.onUpdateAsync(deltaTime);
        }

        for (var child : children) {
            var go = (GameObjectImpl) child;
            go.doOnUpdateAsync(deltaTime);
        }
    }

    private void doOnPreRenderAsync(Void deltaTime) {
        if (!meta().isActive()) {
            return;
        }

        for (var component : components.getComponents()) {
            if (!component.isComponentActive()) {
                return;
            }
            component.onPreRenderAsync();
        }

        for (var child : children) {
            var go = (GameObjectImpl) child;
            go.doOnPreRenderAsync(deltaTime);
        }
    }

    private void doOnRender(Void v) {
        if (!meta().isVisible()) {
            return;
        }

        for (var component : components.getComponents()) {
            if (!component.isComponentActive()) {
                return;
            }
            try
            {
                component.onRender();
            }
            catch (EmptyException e)
            {

            }

        }

        for (var child : children) {
            var go = (GameObjectImpl) child;
            go.doOnRender(v);
        }
    }


    private void doOnDestroy(Void v) {
        for (var component : components.getComponents()) {
            component.onDestroy();
        }

        for (var child : children) {
            var go = (GameObjectImpl) child;
            go.doOnDestroy(v);
        }
    }

    public <T extends Component> T addComponent(Class<T> component)
    {
        return this.components().addComponent(component);
    }

    public void setParent(GameObject newParent)
    {
        if(this.parent != null)
        {
            this.parent.children().remove(this);
        }

        if(newParent == null)
        {
            this.parent = null;
        }

        newParent.children().add(this);
        this.parent = newParent;
    }



    @Override
    public Optional<GameObject> parent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public List<GameObject> children() {
        return children;
    }

    @Override
    public GameObjectMetadata meta() {
        return meta;
    }

    @Override
    public EventsManager events() {
        return events;
    }

    @Override
    public ComponentsManager components() {
        return components;
    }

    @Override
    public Transportation transform() {
        return transportation;
    }

    public final Transportation worldTransform()
    {
        var parentOptional = parent();
        if(parentOptional.isEmpty())
        {
           return transform();
        }
        return parentOptional.get().worldTransform().merge(transform());
    }

    @Override
    public PluginLogger logger() {
        return logger;
    }


    @Override
    public void destroy() {

    }
}
