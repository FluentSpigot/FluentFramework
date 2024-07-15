package io.github.jwdeveloper.ff.animations.impl;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.animations.api.FluentAnimationPlayer;
import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.impl.player.TimeLineContextImpl;
import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AnimationPlayerOld implements FluentAnimationPlayer  {

    private FluentTaskFactory taskFactory;
    private CancellationToken cancellationToken;
    private EventGroup<FluentAnimationPlayer> onEnded = new EventGroup<>();
    private EventGroup<FluentAnimationPlayer> onStarted = new EventGroup<>();
    private EventGroup<AnimationNode> onProgress = new EventGroup<>();

    public AnimationPlayerOld(FluentTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
        cancellationToken = taskFactory.createCancelationToken();
    }

    public FluentAnimationPlayer play(FluentAnimation animation, Entity target)
    {
        if(cancellationToken.isCancel())
        {
            return this;
        }
        taskFactory.taskAsync(() ->
        {
           playAnimation(animation, target);
        }, cancellationToken);
        return this;
    }

    public FluentAnimationPlayer loop(FluentAnimation animation, Entity target)
    {

        onEnded.subscribe(fluentAnimationPlayer ->
        {
            play(animation, target);
        });
        play(animation, target);
        return this;
    }

    @Override
    public FluentAnimationPlayer play(FluentAnimation animation, Entity... target) {
        return null;
    }

    public FluentAnimationPlayer stop() {
        if (cancellationToken == null) {
            return this;
        }
        cancellationToken.cancel();
        return this;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }


    public FluentAnimationPlayer onStarted(Consumer<FluentAnimationPlayer> playerConsumer) {
        onStarted.subscribe(playerConsumer);
        return this;
    }


    public FluentAnimationPlayer onProgress(Consumer<AnimationNode> playerConsumer) {
        onProgress.subscribe(playerConsumer);
        return this;
    }


    public FluentAnimationPlayer onEnded(Consumer<FluentAnimationPlayer> playerConsumer) {
        onEnded.subscribe(playerConsumer);
        return this;
    }


    public void playAnimation(FluentAnimation animation, Entity entity) {
        var iterator = animation.getDefaultBone();
        var context = new TimeLineContextImpl();
        context.setEntity(entity);
        context.setTransformation(new TransformationUtility.TransformationBuilder());
        onStarted.invoke(this);
        do {
            cancellationToken.throwIfCancel();
            if (entity.isDead()) {
                cancellationToken.cancel();
                continue;
            }

        /*    var node = iterator.advance();
            onProgress.invoke(node);
            if (node instanceof WaitNode wait) {
                if (entity instanceof Display display) {
                    display.setInterpolationDelay(0);
                    display.setInterpolationDuration(wait.getTicks());
                    display.setTransformation(context.getTransformation().build());
                }
                node.executeAsync(context);
                if(iterator.peek(1) instanceof EndAnimation)
                {
                    break;
                }

                continue;
            }
            if (node instanceof EndAnimation endAnimation)
            {
                if (entity instanceof Display display) {
                    display.setInterpolationDelay(0);
                    display.setTransformation(context.getTransformation().build());
                }
                break;
            }
            node.executeAsync(context);*/
        }
        while (!iterator.isDone() && cancellationToken.isNotCancel());
        onEnded.invoke(this);
    }



    public static Object awaitSpigotTask(Supplier<Object> runnable, CancellationToken ctx) {
        if (Bukkit.getServer().getClass().getName().contains("Mock")) {
            return runnable.get();
        }

        AtomicReference<Object> reference = new AtomicReference<>();
        AtomicBoolean waitForObject = new AtomicBoolean();
        FluentApi.tasks().task(() ->
        {
            reference.set(runnable.get());
            waitForObject.set(true);
        });
        while (!waitForObject.get()) {
            ctx.throwIfCancel();
        }
        return reference.get();
    }
}
