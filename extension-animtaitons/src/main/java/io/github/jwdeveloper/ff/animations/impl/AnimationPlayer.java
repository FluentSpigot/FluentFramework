package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationBoneSearcher;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationPlayer;
import io.github.jwdeveloper.ff.animations.impl.player.TimeLine;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class AnimationPlayer implements FluentAnimationPlayer {

    private final FluentTaskFactory factory;
    private final FluentAnimationBoneSearcher searcher;
    private final Map<String, TimeLine> timelines = new HashMap<>();
    CancellationToken cancellationToken;

    public AnimationPlayer(FluentTaskFactory factory, FluentAnimationBoneSearcher fluentAnimationBoneSearcher) {
        this.factory = factory;
        this.searcher = fluentAnimationBoneSearcher;
    }

    public boolean isPlaying() {
        for (var timeLine : timelines.values()) {
            if (!timeLine.isDone()) {
                return false;
            }
        }
        return true;
    }

    public FluentAnimationPlayer play(FluentAnimation animation, Entity... entity) {

        for (var boneName : animation.getBones().keySet()) {
            var optional = searcher.findBoneEntity(boneName, entity);
            if (optional.isFailed()) {
                FluentLogger.LOGGER.warning("bone", boneName, "not found in entity", entity);
                continue;
            }
            var boneEntity = optional.getObject();
            var boneAnimations = animation.getBone(boneName);
            var timeLine = new TimeLine(boneAnimations, boneEntity, animation.getDurationMs());
            timelines.put(boneName, timeLine);
        }

        cancellationToken = factory.createCancelationToken();
        timelines.forEach((a, b) ->
        {
            factory.taskAsync(() ->
            {
                b.start(cancellationToken);
            }, cancellationToken);
        });
        return this;
    }

    public FluentAnimationPlayer stop() {
        if (cancellationToken == null) {
            return this;
        }
        cancellationToken.cancel();
        return this;
    }

}
