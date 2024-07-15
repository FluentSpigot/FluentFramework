package io.github.jwdeveloper.ff.animations.api;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import java.util.List;

public interface FluentAnimationBuilder {
    FluentAnimationBuilder withName(String animationName);

    FluentAnimationBuilder thenNode(AnimationNode animationNode);

    FluentAnimationBuilder thenWait(int ticks);

    FluentAnimationBuilder thenTransform(Vector transform);

    FluentAnimationBuilder thenTransform(float x, float y, float z);

    FluentAnimationBuilder thenScale(Vector transform);

    FluentAnimationBuilder thenScale(float x, float y, float z);

    FluentAnimationBuilder thenRotate(float angels, Vector transform);

    FluentAnimationBuilder thenRotate(float angels, float x, float y, float z);

    FluentAnimationBuilder thenParticle(Particle particle);

    FluentAnimation build();

    FluentAnimation buildAndRegister();
}
