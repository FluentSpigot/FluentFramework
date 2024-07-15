package io.github.jwdeveloper.ff.animations.impl.player;

import io.github.jwdeveloper.ff.animations.api.FluentAnimation;
import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import io.github.jwdeveloper.ff.core.common.iterator.AbstractIterator;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import lombok.Getter;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;

public class TimeLine {

    @Getter
    private int currentMs;

    @Getter
    private boolean isDone = true;

    private final AbstractIterator<NodeGroup> iterator;

    private final Entity entity;
    private final int totalTime;

    public TimeLine(AbstractIterator<NodeGroup> iterator, Entity entity, int totalTime) {
        this.iterator = iterator;
        this.entity = entity;
        this.totalTime = totalTime;
    }

    public void start(CancellationToken ctx) {
        isDone = false;
        var context = new TimeLineContextImpl();
        context.setEntity(entity);
        context.setTransformation(TransformationUtility.create());
        if ((context.getEntity() instanceof Display display)) {
            context.setTransformation(TransformationUtility.create(display.getTransformation()));
        }
        do {
            var group = iterator.advance();
            currentMs = group.getStartedMs();
            for (var node : group.getNodes()) {
                node.executeAsync(context);
            }

            var deltaTicks = getDeltaTicks(group.getStartedMs(), iterator.peek(1).getStartedMs());
            if(deltaTicks < 0)
            {
                deltaTicks = getDeltaTicks(group.getStartedMs(), totalTime);
            }
            FluentLogger.LOGGER.info("Delta is ",deltaTicks,"started at",group.startedMs);
            if (group.hasNodes() && (context.getEntity() instanceof Display display)) {
                display.setInterpolationDelay(-1);
                display.setInterpolationDuration(deltaTicks);
                display.setTransformation(context.getTransformation().build());
            }

            var waitMs = ticksToMilliseconds(deltaTicks);
            wait(waitMs);
        }
        while (iterator.hasNext() && ctx.isNotCancel());

        isDone = true;
    }


    private int getDeltaTicks(int startMs, int stopMs) {
        return millisecondsToTicks(stopMs - startMs);
    }

    public static int millisecondsToTicks(long milliseconds) {
        return (int) (milliseconds / 50);
    }

    public static int ticksToMilliseconds(int ticks) {
        return ticks * 50;
    }

    private void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {

        }
    }
}
