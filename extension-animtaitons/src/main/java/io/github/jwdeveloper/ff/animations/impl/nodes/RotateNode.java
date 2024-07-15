package io.github.jwdeveloper.ff.animations.impl.nodes;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.api.nodes.TimelineContext;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import lombok.Getter;
import org.bukkit.util.Vector;

@Getter
public class RotateNode implements AnimationNode {
    private final Vector vector;
    private final float angle;

    public RotateNode(Vector vector, float angle) {
        this.vector = vector;
        this.angle = angle;
    }

    @Override
    public void executeAsync(TimelineContext nodeExecution) {
        FluentLogger.LOGGER.info("rotating!", vector, angle);
        nodeExecution
                .getTransformation()
                .setRightRotation(angle, (float) vector.getX(), (float) vector.getY(), (float) vector.getZ());
    }


}
