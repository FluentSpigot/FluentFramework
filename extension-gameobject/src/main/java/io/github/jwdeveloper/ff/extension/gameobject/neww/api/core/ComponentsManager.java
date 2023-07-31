package io.github.jwdeveloper.ff.extension.gameobject.neww.api.core;

import io.github.jwdeveloper.ff.core.common.Reference;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ComponentsManager {
    <T extends Component> T addComponent(Class<T> component);


    List<Component> findComponents(Predicate<Component> search, boolean deep);

    void findComponents(Predicate<Component> search, boolean deep, List<Component> output);


    <T extends Component> T getComponent(Class<T> type);

    <T extends Component> Optional<T> tryGetComponent(Class<T> type);

    <T extends Component> boolean tryGetComponent(Class<T> type, Reference<T> out);

    List<Component> getComponents();

    <T extends Component> void getComponents(Class<T> type, List<T> result);

    <T extends Component> List<T> getComponents(Class<T> type);


    <T extends Component> T getComponentInChildren(Class<T> type);

    <T extends Component> List<T> getComponentsInChildren(Class<T> type);

    <T extends Component> void getComponentsInChildren(Class<T> type, List<T> output);


    <T extends Component> T getComponentInParent(Class<T> type);

    <T extends Component> List<T> getComponentsInParent(Class<T> type);

    <T extends Component> void getComponentsInParent(Class<T> type, List<T> output);
}
