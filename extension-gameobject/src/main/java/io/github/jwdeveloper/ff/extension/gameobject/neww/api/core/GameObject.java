package io.github.jwdeveloper.ff.extension.gameobject.neww.api.core;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;

import java.util.List;
import java.util.Optional;


public interface GameObject {
    void destroy();

    void setParent(GameObject parent);

    void setVisible(boolean visible);

    Optional<GameObject> parent();

    List<GameObject> children();

    GameObjectMetadata meta();

    EventsManager events();

    ComponentsManager components();

    PluginLogger logger();

    Transportation transform();
    Transportation worldTransform();
     <T extends Component> T addComponent(Class<T> component);


}
