package io.github.jwdeveloper.ff.core.spigot.particles;

import io.github.jwdeveloper.ff.core.spigot.particles.api.ParticleSettings;
import io.github.jwdeveloper.ff.core.spigot.particles.implementation.builder.SimpleParticleBuilder;
import org.bukkit.plugin.Plugin;

public class FluentParticle
{
    public static SimpleParticleBuilder create(Plugin plugin)
    {
        return new SimpleParticleBuilder(new ParticleSettings(plugin));
    }
}
