package io.github.jwdeveloper.ff.core.spigot.particles.implementation;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleInvoker
{
    private final SimpleParticle simpleParticle;
    public ParticleInvoker(SimpleParticle simpleParticle) {
        this.simpleParticle = simpleParticle;
    }

    public void stop()
    {
        simpleParticle.stop();
    }

    public void spawnParticle(ParticleEvent particleEvent)
    {
        particleEvent.getOriginLocation().getWorld().spawnParticle(particleEvent.getParticle(),particleEvent.originLocation,particleEvent.amount);
    }

    public void spawnParticle(Location location, Particle particle,int amount)
    {
        location.getWorld().spawnParticle(particle,location,amount);
    }


    public void spawnParticle(Location location, Particle particle)
    {
        location.getWorld().spawnParticle(particle,location,1);
    }

    public void spawnParticle(Location location, Particle particle, int amount, Particle.DustOptions dustOptions)
    {
        location.getWorld().spawnParticle(particle,location,amount, dustOptions);
    }

    public void spawnDustParticle(ParticleEvent particleEvent)
    {

    }

    public void spawnDustParticle(Location location, Particle particle, Color color)
    {

    }
}
