package io.github.jwdeveloper.ff.animations.impl.nodes;

import io.github.jwdeveloper.ff.animations.api.nodes.AnimationNode;
import io.github.jwdeveloper.ff.animations.api.nodes.TimelineContext;
import lombok.Getter;

public class WaitNode implements AnimationNode {

    @Getter
    private int ticks;

    public WaitNode(int time) {
        this.ticks = time;
    }


    public int getStartMs()
    {
        return (1000/20)*ticks;
    }

    @Override
    public void executeAsync(TimelineContext nodeExecution) {


        var wait = ticks * 1000 / 20;

        try {
            Thread.sleep(wait);
        } catch (Exception e) {

        }
    }


}
