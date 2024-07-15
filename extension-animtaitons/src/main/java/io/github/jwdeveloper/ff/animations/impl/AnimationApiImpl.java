package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationBuilder;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationPlayer;
import io.github.jwdeveloper.ff.animations.impl.parsers.AnimationParser;
import io.github.jwdeveloper.ff.animations.impl.parsers.BlockBenchParser;
import io.github.jwdeveloper.ff.animations.impl.player.TimeLine;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.List;

public class AnimationApiImpl implements AnimationApi {
    private final FluentTaskFactory factory;
    private final AnimationRegistry registry;

    public AnimationApiImpl(FluentTaskFactory factory, AnimationRegistry registry) {
        this.factory = factory;
        this.registry = registry;
    }




    public List<FluentAnimation> loadFromYaml(String json)
    {
        var parser= FluentApi.container().findInjection(BlockBenchParser.class);
        var animations = parser.parse(json);
        return animations;
    }

    public List<FluentAnimation> loadFromBlockBench(String json)
    {
        var parser= FluentApi.container().findInjection(BlockBenchParser.class);
        var animations = parser.parse(json);
        return animations;
    }

    @Override
    public List<FluentAnimation> loadFromYaml(ConfigurationSection section) {
        var parser= FluentApi.container().findInjection(AnimationParser.class);
        var animations =  parser.parse(section);
        return animations;
    }

    @Override
    public FluentAnimationPlayer newAnimationPlayer() {
        return FluentApi.container().findInjection(FluentAnimationPlayer.class);
    }

    @Override
    public FluentAnimationPlayer playAnimation(FluentAnimation animation, Entity... entity) {
        return newAnimationPlayer().play(animation, entity);
    }

    @Override
    public FluentAnimationBuilder createAnimation() {
        return FluentApi.container().findInjection(FluentAnimationBuilder.class);
    }

    @Override
    public ActionResult<FluentAnimation> find(String name) {
        return ActionResult.fromOptional(registry.findByName(name));
    }
}
