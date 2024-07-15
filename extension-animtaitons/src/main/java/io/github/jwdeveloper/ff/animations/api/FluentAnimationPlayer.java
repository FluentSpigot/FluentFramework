package io.github.jwdeveloper.ff.animations.api;

import org.bukkit.entity.Entity;

public interface FluentAnimationPlayer
{
    FluentAnimationPlayer play(FluentAnimation animation, Entity ... target);
    FluentAnimationPlayer stop();
     boolean isPlaying();
}
