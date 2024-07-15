package io.github.jwdeveloper.ff.animations.api;

import io.github.jwdeveloper.ff.animations.impl.player.TimeLine;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.List;

public interface AnimationApi {

    FluentAnimationPlayer newAnimationPlayer();
    FluentAnimationPlayer playAnimation(FluentAnimation animation, Entity ... entity);

    List<FluentAnimation> loadFromYaml(ConfigurationSection section);

    List<FluentAnimation> loadFromBlockBench(String json);

    FluentAnimationBuilder createAnimation();

    ActionResult<FluentAnimation> find(String name);

}
