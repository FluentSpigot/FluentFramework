package io.github.jwdeveloper.ff.models.api.data;

import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import java.util.List;

public interface Bone {
    List<BoneCube> getCubes();
    Vector getPivot();
    String getName();
}
