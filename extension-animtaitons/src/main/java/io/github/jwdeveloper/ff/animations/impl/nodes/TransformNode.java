package io.github.jwdeveloper.ff.animations.impl.nodes;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.api.nodes.TimelineContext;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import lombok.Getter;
import org.bukkit.util.Vector;

@Getter
public class TransformNode implements AnimationNode {

    private final Vector vector;

    public TransformNode(Vector vector) {
        this.vector = vector;
    }

    @Override
    public void executeAsync(TimelineContext nodeExecution) {
        nodeExecution.getTransformation()
                .setTranslation(vector);
    }


}
