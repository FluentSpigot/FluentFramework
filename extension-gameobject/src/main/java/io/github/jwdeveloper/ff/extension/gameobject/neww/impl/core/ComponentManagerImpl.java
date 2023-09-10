package io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core;

import io.github.jwdeveloper.ff.core.common.Reference;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.Component;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.ComponentsManager;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.GameObject;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.exception.ComponentException;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

public class ComponentManagerImpl implements ComponentsManager {
    private final GameObject root;
    private final Map<Class<?>, Component> components;
    public final Queue<Component> componentsToInitialize;

    public ComponentManagerImpl(GameObject root) {
        this.root = root;
        components = new HashMap<>();
        componentsToInitialize = new LinkedBlockingQueue<>();
    }


    public <T extends Component> T addComponent(Class<T> component) {
        if (components.containsKey(component)) {
            return null;
        }

        var componentObj = FluentApi.container().tryFindInjection(component);
        if (componentObj == null) {
            return null;
        }


        if(componentObj instanceof GameComponent gc)
        {

            gc.init(root);
        }



        componentsToInitialize.add(componentObj);
        components.put(component, componentObj);

        return componentObj;
    }


    @Override
    public List<Component> findComponents(Predicate<Component> search, boolean deep) {
        var result = new ArrayList<Component>();
        findComponents(search, deep, result);
        return result;
    }

    @Override
    public void findComponents(Predicate<Component> search, boolean deep, List<Component> output) {
        var componentsList = components.values();
        var result = componentsList.stream().filter(search).toList();
        output.addAll(result);

        if (!deep) {
            return;
        }

        for (var child : root.children()) {
            child.components().findComponents(search, deep, output);
        }
    }

    public <T extends Component> T getComponent(Class<T> type) {
        if (!components.containsKey(type)) {
            throw new ComponentException(type, "Components");
        }
        return (T) components.get(type);
    }

    @Override
    public <T extends Component> Optional<T> tryGetComponent(Class<T> type) {
        try {
            return Optional.of((T) getComponent(type));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public <T extends Component> boolean tryGetComponent(Class<T> type, Reference<T> out) {
        try {
            var result = getComponent(type);
            out.set(result);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Component> getComponents() {
        return components.values().stream().toList();
    }


    @Override
    public <T extends Component> void getComponents(Class<T> type, List<T> result) {
        for (var component : components.entrySet()) {
            if (!component.getKey().equals(type)) {
                continue;
            }
            result.add((T) component.getValue());
        }
    }

    @Override
    public <T extends Component> List<T> getComponents(Class<T> type) {
        var result = new ArrayList<T>();
        getComponents(type, result);
        return result;
    }


    @Override
    public <T extends Component> T getComponentInChildren(Class<T> type) {
        var result = getComponentsInChildren(type);
        if (result.isEmpty()) {
            throw new ComponentException(type, "Child");
        }
        return result.get(0);
    }

    @Override
    public <T extends Component> List<T> getComponentsInChildren(Class<T> type) {
        var result = new ArrayList<T>();
        getComponentsInChildren(type, result);
        return result;
    }

    @Override
    public <T extends Component> void getComponentsInChildren(Class<T> type, List<T> output) {
        for (var child : root.children()) {
            child.components().getComponents(type, output);
            child.components().getComponentsInChildren(type, output);
        }
    }

    @Override
    public <T extends Component> T getComponentInParent(Class<T> type) {
        var result = getComponentsInParent(type);
        if (result.isEmpty()) {
            throw new ComponentException(type, "Parent");
        }
        return result.get(0);
    }

    @Override
    public <T extends Component> List<T> getComponentsInParent(Class<T> type) {
        var result = new ArrayList<T>();
        getComponentsInParent(type, result);
        return result;
    }

    @Override
    public <T extends Component> void getComponentsInParent(Class<T> type, List<T> output) {
        var parentOptional = root.parent();
        if (parentOptional.isEmpty()) {
            return;
        }
        var parent = parentOptional.get();
        parent.components().getComponents(type, output);
    }


}
