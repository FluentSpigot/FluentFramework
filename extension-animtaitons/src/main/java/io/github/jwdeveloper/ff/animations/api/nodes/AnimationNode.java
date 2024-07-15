package io.github.jwdeveloper.ff.animations.api.nodes;

public interface AnimationNode {
    /**
     * This is not running in the spigot thread!
     *
     * @param nodeExecution
     */
    void executeAsync(TimelineContext nodeExecution);
}
