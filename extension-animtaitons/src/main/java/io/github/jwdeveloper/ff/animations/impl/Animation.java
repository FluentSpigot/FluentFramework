package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.impl.data.AnimationData;
import io.github.jwdeveloper.ff.animations.impl.nodes.EmptyNode;
import io.github.jwdeveloper.ff.animations.impl.nodes.EndAnimation;
import io.github.jwdeveloper.ff.animations.impl.player.NodeGroup;
import io.github.jwdeveloper.ff.core.common.iterator.AbstractIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Animation implements FluentAnimation {
    private final AnimationData animationData;

    public Animation(AnimationData animationData) {
        this.animationData = animationData;
    }


    public int getDurationMs() {
        return animationData.getTotalTime();
    }

    public AbstractIterator<NodeGroup> getBone(String name) {
        return new AbstractIterator<>(animationData.getBone(name), new NodeGroup());
    }
    @Override
    public AbstractIterator<NodeGroup> getDefaultBone() {
        return getBone("default");
    }

    @Override
    public String getName() {
        return animationData.getName();
    }

    @Override
    public Map<String, List<NodeGroup>> getBones() {
        return animationData.getNodeGroups();
    }

    @Override
    public boolean hasBone(String name) {
        return animationData.getNodeGroups().containsKey(name);
    }


}
