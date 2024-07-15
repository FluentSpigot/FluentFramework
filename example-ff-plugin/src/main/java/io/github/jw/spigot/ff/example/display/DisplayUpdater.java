package io.github.jw.spigot.ff.example.display;

import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.entity.Display;

public class DisplayUpdater {

    private int delay = 0;
    private Display display;

    private TransformationUtility.TransformationBuilder builder;

    public static DisplayUpdater create(Display display, int rate) {
        var updater = new DisplayUpdater();
        updater.setDisplay(display);
        updater.setDelay(rate);
        return updater;
    }

    public void setDelay(int i) {
        this.delay = i;
    }

    public void setDisplay(Display display) {
        this.display = display;
        this.builder = TransformationUtility.create(display.getTransformation());
    }

    public TransformationUtility.TransformationBuilder builder() {
        return builder;
    }

    public void start() {
        FluentApi.tasks().taskAsync((token) ->
        {
            while (token.isNotCancel()) {
                if (display == null) {
                    continue;
                }
                if(display.isDead())
                {
                    token.cancel();
                    break;
                }
                try {
                    var ticksDefayl = (delay) * (1000 / 20);
                    Thread.sleep(ticksDefayl-10);
                    display.setInterpolationDelay(-1);
                    display.setInterpolationDuration(delay);
                    display.setTransformation(builder.build());
                } catch (Exception e) {
                    FluentLogger.LOGGER.error("Errors", e);
                }
            }
        });
    }
}
