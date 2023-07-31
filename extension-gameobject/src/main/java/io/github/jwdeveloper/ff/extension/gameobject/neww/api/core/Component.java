package io.github.jwdeveloper.ff.extension.gameobject.neww.api.core;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public interface Component {

    boolean isComponentActive();

    GameObject gameobject();

    Transportation transform();

    ComponentsManager components();

    PluginLogger logger();

    void onInitialization(FluentApiSpigot api);

    void onEnable();

    void onPhisicsAsync();

    void onUpdateAsync(double deltaTime);

    void onPreRenderAsync();

    void onRender();

    void onDestroy();
}
