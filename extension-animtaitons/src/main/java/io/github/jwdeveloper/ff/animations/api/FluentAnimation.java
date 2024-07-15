package io.github.jwdeveloper.ff.animations.api;

import io.github.jwdeveloper.ff.animations.impl.player.NodeGroup;
import io.github.jwdeveloper.ff.core.common.iterator.AbstractIterator;

import java.util.List;
import java.util.Map;

public interface FluentAnimation {

     AbstractIterator<NodeGroup> getBone(String name);

    AbstractIterator<NodeGroup> getDefaultBone();
    int getDurationMs();
    String getName();
    Map<String, List<NodeGroup>> getBones();
    boolean hasBone(String name);
}
