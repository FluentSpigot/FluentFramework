package io.github.jwdeveloper.ff.animations.impl.nodes;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.api.nodes.TimelineContext;
import io.github.jwdeveloper.ff.core.spigot.SpigotUtils;
import lombok.Getter;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

@Getter
public class ParticleNode implements AnimationNode {
    private Vector offset;
    private int count;
    private Particle particle;

    public ParticleNode(Vector offset, int count, Particle particle) {
        this.offset = offset;
        this.count = count;
        this.particle = particle;
    }

    @Override
    public void executeAsync(TimelineContext nodeExecution) {
        var world = nodeExecution.getEntity().getWorld();
        var location = nodeExecution.getEntity().getLocation().add(offset);

        if(SpigotUtils.isMock())
        {
            System.out.println("Particle spawned!");
            return;
        }

        world.spawnParticle(particle, location, count);
    }
}
