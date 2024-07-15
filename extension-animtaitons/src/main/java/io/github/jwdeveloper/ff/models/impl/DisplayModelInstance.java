package io.github.jwdeveloper.ff.models.impl;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModel;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModelInstance;
import io.github.jwdeveloper.ff.models.impl.entitis.EntitisManager;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Location;

public class DisplayModelInstance implements FluentDisplayModelInstance {
    private Location location;
    private final FluentDisplayModel model;
    private final EntitisManager entitiManager;

    public DisplayModelInstance(Location location, FluentDisplayModel fluentDisplayModel) {
        this.location = location;
        this.model = fluentDisplayModel;
        this.entitiManager = new EntitisManager(location, fluentDisplayModel);
    }

    @Override
    public FluentDisplayModel getModel() {
        return model;
    }

    @Override
    public void playAnimation(FluentAnimation animation) {

        FluentApi.container().findInjection(AnimationApi.class).playAnimation(animation, entitiManager.getAll());
    }

    public void teleport(Location location) {
        this.location = location;
        entitiManager.teleport(location);
    }

    @Override
    public void remove() {
        entitiManager.remove();
    }
}
