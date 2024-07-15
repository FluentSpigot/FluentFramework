package io.github.jwdeveloper.ff.models.impl;

import io.github.jwdeveloper.ff.models.api.data.Bone;
import io.github.jwdeveloper.ff.models.api.data.BoneCube;
import lombok.Data;
import org.bukkit.util.Vector;

import java.util.List;

@Data
public class DisplayBone implements Bone {

    private final List<BoneCube> cubes;
    private final Vector pivot;
    private final String name;


}
