package io.github.jwdeveloper.ff.animations.impl.player;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.core.common.iterator.AbstractIterator;

import java.util.List;

public class NodeIterator extends AbstractIterator<AnimationNode>
{
    public NodeIterator(List<AnimationNode> target, AnimationNode defaultValue) {
        super(target, defaultValue);
    }
}
