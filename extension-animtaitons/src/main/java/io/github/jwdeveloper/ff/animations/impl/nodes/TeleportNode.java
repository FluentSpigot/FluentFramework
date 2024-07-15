package io.github.jwdeveloper.ff.animations.impl.nodes;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.api.nodes.TimelineContext;
import lombok.Getter;
import org.bukkit.util.Vector;

@Getter
public class TeleportNode implements AnimationNode {

    private final Vector vector;

    public TeleportNode(Vector vector) {
        this.vector = vector;
    }
    @Override
    public void executeAsync(TimelineContext nodeExecution) {

    }
}
