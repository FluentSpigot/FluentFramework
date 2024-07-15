package io.github.jwdeveloper.ff.models.impl;

import io.github.jwdeveloper.ff.models.api.FluentDisplayModel;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModelInstance;
import io.github.jwdeveloper.ff.models.api.data.Bone;
import org.bukkit.Location;

import java.util.Map;

public class DisplayModel implements FluentDisplayModel {

    private final DisplayModelData data;

    public DisplayModel(DisplayModelData data) {
        this.data = data;
    }

    @Override
    public String getName() {
        return data.getName();
    }

    @Override
    public boolean hasBone(String name) {
        return data.getBones().containsKey(name);
    }

    @Override
    public Bone getBone(String name) {
        return data.getBones().get(name);
    }

    @Override
    public Map<String, Bone> getBones() {
        return data.getBones();
    }

    @Override
    public FluentDisplayModelInstance spawn(Location location) {
        return new DisplayModelInstance(location, this);
    }
}
