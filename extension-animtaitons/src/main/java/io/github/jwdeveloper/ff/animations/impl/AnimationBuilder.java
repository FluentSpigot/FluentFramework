package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationBuilder;
import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.impl.data.AnimationData;
import io.github.jwdeveloper.ff.animations.impl.nodes.*;
import io.github.jwdeveloper.ff.animations.impl.parsers.AnimationParser;
import io.github.jwdeveloper.ff.animations.impl.parsers.BlockBenchParser;
import io.github.jwdeveloper.ff.animations.impl.parsers.NodeGroupParser;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class AnimationBuilder implements FluentAnimationBuilder {

    private final AnimationData data;
    private final AnimationRegistry animationRegistry;
    private final List<AnimationNode> nodes;

    public AnimationBuilder(AnimationRegistry animationRegistry) {
        this.animationRegistry = animationRegistry;
        this.nodes = new ArrayList<>();
        this.data = new AnimationData();
        this.data.setName("animation");
    }

    @Override
    public FluentAnimationBuilder withName(String animationName) {
        data.setName(animationName);
        return this;
    }

    @Override
    public FluentAnimationBuilder thenNode(AnimationNode animationNode) {
        nodes.add(animationNode);
        return this;
    }

    @Override
    public FluentAnimationBuilder thenWait(int ticks) {
        return thenNode(new WaitNode(ticks));
    }

    @Override
    public FluentAnimationBuilder thenTransform(Vector transform) {
        return thenNode(new TransformNode(transform));
    }

    @Override
    public FluentAnimationBuilder thenTransform(float x, float y, float z) {
        return thenTransform(new Vector(x, y, z));
    }

    @Override
    public FluentAnimationBuilder thenScale(Vector transform) {
        return thenNode(new ScaleNode(transform));
    }

    @Override
    public FluentAnimationBuilder thenScale(float x, float y, float z) {
        return thenScale(new Vector(x, y, z));
    }

    @Override
    public FluentAnimationBuilder thenRotate(float angels, Vector transform) {
        thenNode(new RotateNode(transform, angels));
        return this;
    }

    @Override
    public FluentAnimationBuilder thenRotate(float angels, float x, float y, float z) {
        thenNode(new RotateNode(new Vector(x, y, z), angels));
        return this;
    }

    @Override
    public FluentAnimationBuilder thenParticle(Particle particle) {
        return thenNode(new ParticleNode(new Vector(0, 0, 0), 1, particle));
    }


    public FluentAnimation build() {
        var parser = new NodeGroupParser();
        var nodeGroups = parser.toNodeGroup(nodes);
        data.addGroup("default", nodeGroups);
        var animation = new Animation(data);
        return animation;
    }

    @Override
    public FluentAnimation buildAndRegister() {
        var animaton = build();
        animationRegistry.register(animaton);
        return animaton;
    }


}
