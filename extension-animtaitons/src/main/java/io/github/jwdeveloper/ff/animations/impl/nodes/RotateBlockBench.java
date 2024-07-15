package io.github.jwdeveloper.ff.animations.impl.nodes;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.api.nodes.TimelineContext;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;

public class RotateBlockBench implements AnimationNode {

    private final Vector vector;

    public RotateBlockBench(Vector vector) {
        this.vector = vector;
    }

    @Override
    public void executeAsync(TimelineContext nodeExecution) {

        var quaternion = new Quaternionf().rotationXYZ(
                (float) Math.toRadians(vector.getX()),
                (float) Math.toRadians(vector.getY()),
                (float) Math.toRadians(vector.getZ())
        );
        nodeExecution.getTransformation().setRightRotation(quaternion);

    }
}
