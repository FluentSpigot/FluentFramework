package io.github.jwdeveloper.ff.animations.impl.data.custom;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.impl.nodes.*;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class NodeDataMapper {
    private final Map<String, Function<NodeData, AnimationNode>> profiles = new HashMap<>();

    public NodeDataMapper() {
        init();
    }

    private void init() {

        addProfile(data ->
        {
            var vector = toVector(data);
            return new TransformNode(vector);
        }, "t", "translate");
        addProfile(data ->
        {
            var vector = toVector(data);
            return new ScaleNode(vector);
        }, "s", "scale");
        addProfile(data ->
        {
            var vector = toVector(data);
            var angle = data.getFloat("angle");
            return new RotateNode(vector, (int) angle);
        }, "r", "rotate");
        addProfile(data ->
        {
            var ticks = data.getFloat("ticks");
            return new WaitNode((int) ticks);
        }, "w", "wait");

        addProfile(data ->
        {
            var vector = toVector(data);
            return new TeleportNode(vector);
        }, "tp", "teleport");

        addProfile(data ->
        {
            var vector = toVector(data);
            var count = data.getFloat("count");
            count = count <= 0 ? 1 : count;

            var particleType = data.getOrDefault("particle", "heart");
            var particle = Particle.valueOf(particleType.toUpperCase());
            return new ParticleNode(vector, (int) count, particle);
        }, "p", "particles");
    }

    public AnimationNode map(NodeData data) {
        var name = data.getName();
        if (!profiles.containsKey(name)) {
            return new EmptyNode();
        }
        var mapper = profiles.get(name);
        return mapper.apply(data);
    }

    private Vector toVector(NodeData data) {
        var x = data.getFloat("x");
        var y = data.getFloat("y");
        var z = data.getFloat("z");
        return new Vector(x, y, z);
    }

    private void addProfile(Function<NodeData, AnimationNode> mapper, String... nodeTypes) {
        for (var type : nodeTypes) {
            profiles.put(type, mapper);
        }
    }
}
